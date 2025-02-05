package cn.iocoder.yudao.module.fx.service.inventorydata;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.inventorydata.vo.InventoryDataPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.inventorydata.vo.InventoryDataSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.inventorydata.InventoryDataDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.inventorydata.InventoryDataResponse;
import cn.iocoder.yudao.module.fx.dal.dataobject.inventorydata.InventoryVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository.SendRepositoryDO;
import cn.iocoder.yudao.module.fx.dal.mysql.inventorydata.InventoryDataMapper;
import cn.iocoder.yudao.module.fx.service.sendrepository.SendRepositoryService;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import cn.iocoder.yudao.module.fx.utils.MapUtils;
import cn.iocoder.yudao.module.system.service.dict.DictDataService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jushuitan.api.ApiClient;
import com.jushuitan.api.ApiRequest;
import com.jushuitan.api.ApiResponse;
import com.jushuitan.api.DefaultApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.INVENTORY_DATA_NOT_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DICT_TYPE_NOT_EXISTS;

/**
 * 分销商品库存 Service 实现类
 *
 * @author 管理员
 */
@Slf4j
@Service
@Validated
public class InventoryDataServiceImpl implements InventoryDataService {

    @Resource
    private InventoryDataMapper inventoryDataMapper;
    @Resource
    private SendRepositoryService sendRepositoryService;
    @Resource
    private DictDataService dictDataService;

    @Override
    public Integer createInventoryData(InventoryDataSaveReqVO createReqVO) {
        // 插入
        InventoryDataDO inventoryData = BeanUtils.toBean(createReqVO, InventoryDataDO.class);
        inventoryDataMapper.insert(inventoryData);
        // 返回
        return inventoryData.getId();
    }

    @Override
    public void updateInventoryData(InventoryDataSaveReqVO updateReqVO) {
        // 校验存在
        validateInventoryDataExists(updateReqVO.getId());
        // 更新
        InventoryDataDO updateObj = BeanUtils.toBean(updateReqVO, InventoryDataDO.class);
        inventoryDataMapper.updateById(updateObj);
    }

    @Override
    public void deleteInventoryData(Integer id) {
        // 校验存在
        validateInventoryDataExists(id);
        // 删除
        inventoryDataMapper.deleteById(id);
    }

    private void validateInventoryDataExists(Integer id) {
        if (inventoryDataMapper.selectById(id) == null) {
            throw exception(INVENTORY_DATA_NOT_EXISTS);
        }
    }

    @Override
    public InventoryDataDO getInventoryData(Integer id) {
        return inventoryDataMapper.selectById(id);
    }

    @Override
    public PageResult<InventoryDataDO> getInventoryDataPage(InventoryDataPageReqVO pageReqVO) {
        return inventoryDataMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncInventoryData() throws InterruptedException {
        //从数据字典获取接口数据
        Map<String, String> apiInfo = dictDataService.getDictDataMapByDictType("fx_jushuitan_API_info");
        if (MapUtils.isEmpty(apiInfo)) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        String url = apiInfo.get("InventoryDataSyncUrl");
        String appKey = apiInfo.get("appKey");
        String appSecret = apiInfo.get("appSecret");
        String accessToken = apiInfo.get("accessToken");
        LocalDateTime now = LocalDateTime.now();
        List<SendRepositoryDO> sendRepositoryList = sendRepositoryService.getSendRepositoryList();
        for (SendRepositoryDO sendRepositoryDO : sendRepositoryList) {
            //获取分仓仓库id
            String wmsCoId = sendRepositoryDO.getCode();
            log.info("仓库编码：{}", wmsCoId);
            //递归获取所有库存信息
            TimeUnit.MILLISECONDS.sleep(1000);
            executeInventoryData(1, now.minusDays(2), now, url, appKey, appSecret, accessToken, wmsCoId, sendRepositoryDO);
        }
    }

    private void executeInventoryData(int pageNum, LocalDateTime modifiedBegin, LocalDateTime modifiedEnd, String url, String appKey, String appSecret, String accessToken, String wmsCoId, SendRepositoryDO sendRepositoryDO) {
        // 实例化client
        ApiClient client = new DefaultApiClient();
        String biz = String.format("{\"page_num\":\"%s\",\"page_size\":\"100\",\"wms_co_id\":\"%s\",\"modified_begin\":\"%s\",\"modified_end\":\"%s\"}", pageNum, wmsCoId, modifiedBegin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), modifiedEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 构建请求对象
        ApiRequest request = new ApiRequest.Builder(url, appKey, appSecret)
                .biz(biz).build();
        // 执行接口调用
        try {
            ApiResponse response = client.execute(request, accessToken);
            String body = response.getBody();
            log.info(body);
            InventoryDataResponse bodyMO = JSONObject.parseObject(body, InventoryDataResponse.class);
            if (0 == bodyMO.getCode()) {
                List<InventoryVO> datas = bodyMO.getData().getInventorys();
                if (CollectionUtil.isNotEmpty(datas)) {
                    // 把库存信息存库
                    List<InventoryDataDO> list = new ArrayList<>(datas.size());
                    Set<String> codes = new HashSet<>(datas.size());
                    for (InventoryVO data : datas) {
                        codes.add(data.getSkuId());
                    }
                    List<InventoryDataDO> existingInfos = inventoryDataMapper.selectList(new LambdaQueryWrapper<InventoryDataDO>().in(InventoryDataDO::getSkuId, codes).eq(InventoryDataDO::getWarehouseCode, wmsCoId));
                    Map<String, InventoryDataDO> existingInfoMap = existingInfos.stream().collect(Collectors.toMap(InventoryDataDO::getSkuId, Function.identity()));
                    for (InventoryVO data : datas) {
                        InventoryDataDO one = existingInfoMap.get(data.getSkuId());
                        if (one == null) {
                            one = new InventoryDataDO();
                            existingInfoMap.put(data.getSkuId(), one);
                        }
                        one.setUpdateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
                        one.setCreateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
                        one.setWarehouseCode(wmsCoId);
                        one.setWarehouseName(sendRepositoryDO.getName());
                        one.setWarehouseId(String.valueOf(sendRepositoryDO.getId()));
                        BeanUtil.copyProperties(data, one);
                        list.add(one);
                    }
                    inventoryDataMapper.insertOrUpdateBatch(list);
                }
                if (bodyMO.getData().isHasNext()) {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    executeInventoryData(pageNum + 1, modifiedBegin, modifiedEnd, url, appKey, appSecret, accessToken, wmsCoId, sendRepositoryDO);
                }
            } else {
                throw new RuntimeException(bodyMO.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}