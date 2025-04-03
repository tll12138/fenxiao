package cn.iocoder.yudao.module.fx.service.jstorderout;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.jstorderout.vo.JstOrderOutSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstorderout.JstOrderOutDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstorderout.JstOrderOutDTO;
import cn.iocoder.yudao.module.fx.dal.dataobject.manualdelivery.QueryResponseDataItemDo;
import cn.iocoder.yudao.module.fx.dal.dataobject.manualdelivery.QueryResponseDo;
import cn.iocoder.yudao.module.fx.dal.dataobject.manualdelivery.SentResponseDataItemDo;
import cn.iocoder.yudao.module.fx.dal.dataobject.manualdelivery.SentResponseDo;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.module.fx.dal.mysql.jstorderout.JstOrderOutMapper;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.enums.SaleFromEnum;
import cn.iocoder.yudao.module.fx.service.customeraccount.CustomerAccountService;
import cn.iocoder.yudao.module.fx.service.importorder.ImportOrderService;
import cn.iocoder.yudao.module.fx.service.jushuitanapi.JuShuiTanApiService;
import cn.iocoder.yudao.module.fx.service.ordersinfo.OrdersInfoService;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import cn.iocoder.yudao.module.system.api.dict.DictDataApi;
import cn.iocoder.yudao.module.system.service.dict.DictDataService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jushuitan.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.JST_ORDER_OUT_ERROR_EXPRESS_NOT_EXIST;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.JST_ORDER_OUT_ERROR_SALE_FORM_EMPTY;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.JST_ORDER_OUT_NOT_EXISTS;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.MANUAL_DELIVERY_ERROR;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.MANUAL_DELIVERY_JUSHUITAN_ERROR;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.MANUAL_DELIVERY_SO_ID_NOT_EXISTS;

/**
 * 聚水潭发货回传中间表 Service 实现类
 *
 * @author 管理员
 */
@Slf4j
@Service
@Validated
public class JstOrderOutServiceImpl implements JstOrderOutService {

    @Resource
    private JstOrderOutMapper jstOrderOutMapper;
    @Resource
    private JuShuiTanApiService juShuiTanApiService;
    @Resource
    private OrdersInfoService ordersInfoService;
    @Resource
    private DictDataService dictDataService;
    @Resource
    private CustomerAccountService customerAccountService;
    @Resource
    private ImportOrderService importOrderService;
    private static DictDataApi dictDataApi;

    @Override
    public Long createJstOrderOut(JstOrderOutSaveReqVO createReqVO) {
        // 插入
        JstOrderOutDO jstOrderOut = BeanUtils.toBean(createReqVO, JstOrderOutDO.class);
        jstOrderOutMapper.insert(jstOrderOut);
        // 返回
        return jstOrderOut.getId();
    }

    @Override
    public void updateJstOrderOut(JstOrderOutSaveReqVO updateReqVO) {
        // 校验存在
        validateJstOrderOutExists(updateReqVO.getId());
        // 更新
        JstOrderOutDO updateObj = BeanUtils.toBean(updateReqVO, JstOrderOutDO.class);
        jstOrderOutMapper.updateById(updateObj);
    }

    /**
     * 聚水潭订单出库调用
     *
     * @param soId        销售单号
     * @param expressName 快递公司名称
     * @param express     快递单号
     * @param expressCode 快递编码
     */
    @Override
    public void handleCallSaleOrderOut(String soId, String expressName, String express, String expressCode) {
        // 1. 查询出库单信息
        JSONObject queryParams = JSONUtil.createObj().set("so_ids", new String[]{soId});
        ApiResponse queryResponse = juShuiTanApiService.execute("outSimpleQueryUrl", queryParams.toString());
        QueryResponseDo queryResult = parseResponse(queryResponse.getBody(), QueryResponseDo.class);

        // 校验查询响应
        validateERPResponse(queryResult, soId);

        // 2. 获取出库单号（单条发货只会有1条结果）
        QueryResponseDataItemDo firstItem = queryResult.getData().getDatas().get(0);
        Integer ioId = firstItem.getIo_id();

        // 3. 上传发货信息
        JSONObject sentParams = JSONUtil.createObj()
                .set("io_id", ioId)
                .set("lc_name", expressName)
                .set("l_id", express)
                .set("lc_id", expressCode);
        ApiResponse sentResponse = juShuiTanApiService.execute("wmsSentUploadUrl", sentParams.toString());
        SentResponseDo sentResult = parseResponse(sentResponse.getBody(), SentResponseDo.class);

        // 4. 校验发货结果
        validateSentResponse(sentResult);
    }

    // 统一响应解析
    private <T> T parseResponse(String jsonBody, Class<T> clazz) {
        return com.alibaba.fastjson.JSONObject.parseObject(jsonBody, clazz);
    }

    // 统一处理聚水潭错误
    private void validateERPResponse(QueryResponseDo response, String soId) {
        if (response.getCode() != 0) {
            handleERPError(response.getMsg());
        }
        if (CollectionUtil.isEmpty(response.getData().getDatas())) {
            throw exception(MANUAL_DELIVERY_SO_ID_NOT_EXISTS, soId);
        }
    }

    private void handleERPError(String errorMsg) {
        throw exception(MANUAL_DELIVERY_JUSHUITAN_ERROR, errorMsg);
    }

    private void validateSentResponse(SentResponseDo response) {
        if (response.getCode() != 0) {
            handleERPError(response.getMsg());
        }
        SentResponseDataItemDo resultItem = response.getData().getData().get(0);
        if (!resultItem.getIssuccess()) {
            throw exception(MANUAL_DELIVERY_ERROR, resultItem.getMsg());
        }
    }

    private void validateJstOrderOutExists(Long id) {
        if (jstOrderOutMapper.selectById(id) == null) {
            throw exception(JST_ORDER_OUT_NOT_EXISTS);
        }
    }

    /**
     * 根据内部单号创建或更新聚水潭发货回传中间表
     *
     * @param jstOrderOutDO
     * @return
     */
    @Override
    public int mergeOrderOut(JstOrderOutDO jstOrderOutDO) {
        return jstOrderOutMapper.mergeOrderOut(jstOrderOutDO);
    }

    /**
     * 定时任务自动发货和钉钉通知
     */
    @Override
    public void autoSendAndDingTalk() {
        //天猫国际、中免日上、猫超的自动发货
        List<OrdersInfoDO> autoSendOrders = ordersInfoService.getAutoSendOrders();
        autoSendOrders.forEach(order -> {
            this.handleCallSaleOrderOut(order.getOrderId(), "自提", "TMGJ" + RandomUtil.randomInt(100000, 999999), "ZTZS");
        });
        //正常出库发货
        List<JstOrderOutDTO> notTranOrderOut = jstOrderOutMapper.getNotTranOrderOut();
        //从数据字典获取快递公司数据
        Map<String, String> expressInfo = dictDataService.getDictDataMapByDictType("fx_wl");
        for (JstOrderOutDTO orderOut : notTranOrderOut) {
            String dictExpressName = expressInfo.get(orderOut.getExpressCode());
            if (StrUtil.isBlank(dictExpressName)) {
                throw exception(JST_ORDER_OUT_ERROR_EXPRESS_NOT_EXIST, orderOut.getSoId());
            }
            String soFrom = orderOut.getSoFrom();
            if (StrUtil.isBlank(soFrom)) {
                throw exception(JST_ORDER_OUT_ERROR_SALE_FORM_EMPTY, orderOut.getSoId());
            }
            if (SaleFromEnum.SALE.getValue().equals(soFrom)) {
                OrdersInfoDO orderInfo = ordersInfoService.getOrdersInfoByOrderId(orderOut.getSoId());
                if (orderInfo != null) {
                    orderInfo.setSendTime(LocalDate.now())
                            .setSendDate(LocalDate.now())
                            .setOrderStatus(OrderStatusType.SHIPPED.getType())
                            .setLogisticsNumber(orderOut.getExpress())
                            .setLogisticsCompany(orderOut.getExpressCode());
                    ordersInfoService.updateOrdersInfoByDO(orderInfo);
                    //分销商账户扣款
                    BigDecimal balance = customerAccountService.saleReceivable(orderInfo);
                    //发货后余额的提醒
                    String userId = dictDataApi.getDictDataLabel("fx_notice_head", orderInfo.getBusinessBelong());
                    //更新中间表转换标记
                    jstOrderOutMapper.update(new LambdaUpdateWrapper<JstOrderOutDO>()
                            .eq(JstOrderOutDO::getSoId, orderOut.getSoId())
                            .set(JstOrderOutDO::getIsTran, 1));
                    //钉钉通知 TODO

                }
            }
//            else if (SaleFromEnum.MARKET.getValue().equals(soFrom)) {
//
//            } else if (SaleFromEnum.NG.getValue().equals(soFrom)) {
//
//            } else {
//
//            }
        }
        //客商批发单更新单号
        List<JstOrderOutDTO> importOrders = jstOrderOutMapper.getImportOrder();
        for (JstOrderOutDTO orderOut : importOrders) {
            importOrderService.updateImportOrderByJstOut(orderOut);
        }
    }

}