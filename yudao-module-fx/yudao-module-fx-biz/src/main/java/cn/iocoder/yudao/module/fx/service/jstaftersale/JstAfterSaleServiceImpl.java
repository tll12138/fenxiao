package cn.iocoder.yudao.module.fx.service.jstaftersale;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale.AfterSaleRequest;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale.AfterSaleRequestItem;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale.AfterSaleResponse;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale.DataItem2B;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale.DataItem2C;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale.JstAfterSaleDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersaledata.JstAfterSaleDataDO;
import cn.iocoder.yudao.module.fx.dal.mysql.jstaftersale.JstAfterSaleMapper;
import cn.iocoder.yudao.module.fx.service.jstaftersaledata.JstAfterSaleDataService;
import cn.iocoder.yudao.module.fx.service.jushuitanapi.JuShuiTanApiService;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import cn.iocoder.yudao.module.fx.utils.ObjectUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.diboot.core.exception.BusinessException;
import com.jushuitan.api.ApiResponse;
import liquibase.repackaged.org.apache.commons.lang3.exception.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分销退货传聚水潭中间 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class JstAfterSaleServiceImpl implements JstAfterSaleService {

    @Resource
    private JstAfterSaleMapper jstAfterSaleMapper;
    @Resource
    private JuShuiTanApiService juShuiTanApiService;
    @Resource
    private JstAfterSaleDataService jstAfterSaleDataService;

    @Override
    public Long createJstAfterSale(JstAfterSaleDO jstAfterSale) {
        // 插入
        jstAfterSaleMapper.insert(jstAfterSale);
        // 返回
        return jstAfterSale.getId();
    }

    @Override
    public JstAfterSaleDO getJstAfterSale(Long id) {
        return jstAfterSaleMapper.selectById(id);
    }

    /**
     * 调用聚水潭售后api
     */
    @Override
    public void updateCallERP() {
        //批量获取未上传聚水潭的售后单
        List<JstAfterSaleDO> jstAfterSaleDOS = jstAfterSaleMapper
                .selectList(new LambdaQueryWrapper<JstAfterSaleDO>().eq(JstAfterSaleDO::getOrderStatus, 0));
        if (CollectionUtil.isEmpty(jstAfterSaleDOS)) {
            log.info("没有需要同步的售后订单");
            return;
        }
        try {
            Map<String, JstAfterSaleDO> outerAsIdMap = preprocessData(jstAfterSaleDOS);
            List<AfterSaleRequest> collect = buildAfterSaleRequests(jstAfterSaleDOS);
            //根据sourceType分组 2b/2c
            Map<String, List<AfterSaleRequest>> groupMap = collect.stream()
                    .collect(Collectors.groupingBy(AfterSaleRequest::getSourceType));
            groupMap.forEach((sourceType, requestsByType) -> {
                if ("2B".equals(sourceType)) {
                    handle2BRequests(requestsByType, outerAsIdMap);
                } else if ("2C".equals(sourceType)) {
                    handle2CRequests(requestsByType, outerAsIdMap);
                } else {
                    log.warn("发现未知来源类型的售后单: {}", sourceType);
                }
            });
        } catch (BusinessException e) {
            throw e; // 已知业务异常直接抛出
        } catch (Exception e) {
            log.error("聚水潭接口调用异常: {}", ExceptionUtils.getRootCauseMessage(e), e);
            throw new BusinessException("系统处理异常: %s", e.getMessage());
        }
    }

    /**
     * 预处理数据，构建外部ID映射
     */
    private Map<String, JstAfterSaleDO> preprocessData(List<JstAfterSaleDO> list) {
        return list.stream().collect(Collectors.toMap(
                JstAfterSaleDO::getOuterAsId,
                Function.identity(),
                (existing, replacement) -> {
                    log.warn("发现重复的outerAsId: {}", existing.getOuterAsId());
                    return existing;
                }));
    }

    /**
     * 构建售后请求对象
     */
    private List<AfterSaleRequest> buildAfterSaleRequests(List<JstAfterSaleDO> sourceList) {
        return sourceList.stream()
                .map(item -> {
                    AfterSaleRequest request = ObjectUtils.copyProperties(item, AfterSaleRequest.class);
                    request.setSourceType(item.getSourceType());
                    return request;
                })
                .filter(request -> {
                    List<JstAfterSaleDataDO> details = jstAfterSaleDataService
                            .getJstAfterSaleDataListByMainId(request.getId());
                    if (CollectionUtil.isEmpty(details)) {
                        log.warn("无明细的售后请求被过滤，ID: {}", request.getId());
                        return false;
                    }
                    request.setItems(CollectionUtil.copyList(details, AfterSaleRequestItem.class));
                    return true;
                })
                .collect(Collectors.toList());
    }

    /**
     * 处理2B类型请求
     */
    private void handle2BRequests(List<AfterSaleRequest> requests, Map<String, JstAfterSaleDO> idMap) {
        List<JstAfterSaleDO> updateList = requests.stream()
                .map(request -> processSingle2BRequest(request, idMap))
                .collect(Collectors.toList());

        if (!updateList.isEmpty()) {
            jstAfterSaleMapper.updateBatch(updateList);
            log.info("成功处理{}笔2B售后单", updateList.size());
        }
    }

    /**
     * 处理单个2B请求
     */
    private JstAfterSaleDO processSingle2BRequest(AfterSaleRequest request, Map<String, JstAfterSaleDO> idMap) {
        String biz = JSONObject.toJSONString(request);
        log.info("2B请求报文: {}", biz);

        ApiResponse apiResponse = juShuiTanApiService.execute("otherInoutUploadUrl", biz);
        validateApiResponse(apiResponse);

        AfterSaleResponse response = JSON.parseObject(apiResponse.getBody(),
                new TypeReference<AfterSaleResponse>() {
                });
        DataItem2B dataItem2B = response.getData().getDataItem2B();

        JstAfterSaleDO target = idMap.get(dataItem2B.getExternalId());
        return buildUpdateEntity(target, response)
                .setIoId((long) dataItem2B.getIoId())
                .setOrderStatus(1);
//        return new JstAfterSaleDO();
    }

    /**
     * 处理2C类型请求
     */
    private void handle2CRequests(List<AfterSaleRequest> requests, Map<String, JstAfterSaleDO> idMap) {
        String biz = JSONObject.toJSONString(requests);
        log.info("2C批量请求报文: {}", biz);

        ApiResponse apiResponse = juShuiTanApiService.execute("afterSaleUploadUrl", biz);
        validateApiResponse(apiResponse);

        AfterSaleResponse response = JSON.parseObject(apiResponse.getBody(),
                new TypeReference<AfterSaleResponse>() {
                });

        List<JstAfterSaleDO> updateList = response.getData().getDataItem2C().stream()
                .map(item -> processSingle2CItem(item, idMap, response))
                .collect(Collectors.toList());

        if (!updateList.isEmpty()) {
            jstAfterSaleMapper.updateBatch(updateList);
            log.info("成功处理{}笔2C售后单", updateList.size());
        }
    }

    private JstAfterSaleDO processSingle2CItem(DataItem2C item, Map<String, JstAfterSaleDO> idMap, AfterSaleResponse response) {
        JstAfterSaleDO target = idMap.get(item.getOuterAsId());
        if (target == null) {
            throw new BusinessException("无效的outerAsId: %s", item.getOuterAsId());
        }
        return buildUpdateEntity(target, response)
                .setOrderStatus(item.isSuccess() ? 2 : 1)
                .setAsId((long) item.getAsId())
                .setOId((long) item.getOId())
                .setToErpMsg(item.getMsg());
    }


    /**
     * 构建更新实体公共字段
     */
    private JstAfterSaleDO buildUpdateEntity(JstAfterSaleDO target, AfterSaleResponse response) {
        return target.setToErpTime(DateUtil.now())
                .setToErpMsg(response.getMsg());
    }

    /**
     * 验证接口响应
     */
    private void validateApiResponse(ApiResponse response) {
        if (response.getCode() != 0 || response.getBody() == null) {
            throw new BusinessException("聚水潭接口异常: %s", response.getMsg());
        }
    }

}