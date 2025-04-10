package cn.iocoder.yudao.module.fx.service.jstorderout;

import cn.hutool.core.date.DateUtil;
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
import cn.iocoder.yudao.module.system.util.dd.DingTalkUtils;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jushuitan.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.JST_ORDER_OUT_ERROR_EXPRESS_NOT_EXIST;
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
    @Resource
    private DingTalkUtils dingTalkUtils;
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
    @Transactional(rollbackFor = Exception.class)
    public void autoSendAndDingTalk() {
        handleAutoSendOrders();    // 处理自动发货
        processNormalOrders();     // 处理正常出库订单
        processImportOrders();     // 处理客商批发单
    }

    /**
     * 处理自动发货订单（自提订单）
     * 1. 获取需要自动发货的订单列表
     * 2. 为每个订单生成虚拟快递单号
     * 3. 调用聚水潭发货接口完成出库
     */
    private void handleAutoSendOrders() {
        List<OrdersInfoDO> autoSendOrders = ordersInfoService.getAutoSendOrders();
        if (CollectionUtil.isEmpty(autoSendOrders)) {
            log.info("[自动发货] 未找到待处理订单");
            return;
        }

        autoSendOrders.forEach(order -> {
            String expressCode = "TMGJ" + RandomUtil.randomInt(100000, 999999);
            try {
                this.handleCallSaleOrderOut(order.getOrderId(), "自提", expressCode, "ZTZS");
                log.info("[自动发货] 订单{}发货成功，快递单号：{}", order.getOrderId(), expressCode);
            } catch (Exception e) {
                log.error("[自动发货] 订单{}发货失败：{}", order.getOrderId(), e.getMessage());
            }
        });
    }

    /**
     * 处理正常出库订单流程
     * 1. 获取未转译的出库订单
     * 2. 加载物流字典数据
     * 3. 逐个处理订单
     */
    private void processNormalOrders() {
        List<JstOrderOutDTO> notTranOrderOut = jstOrderOutMapper.getNotTranOrderOut();
        if (CollectionUtil.isEmpty(notTranOrderOut)) {
            log.info("[正常出库] 未找到待处理订单");
            return;
        }

        Map<String, String> expressInfo = dictDataService.getDictDataMapByDictType("fx_wl");
        notTranOrderOut.forEach(orderOut -> processSingleOrder(orderOut, expressInfo));
    }

    /**
     * 处理单个订单的分派逻辑
     *
     * @param orderOut    出库订单DTO
     * @param expressInfo 物流字典信息
     */
    private void processSingleOrder(JstOrderOutDTO orderOut, Map<String, String> expressInfo) {
        try {
            validateExpressInfo(orderOut, expressInfo);
            SaleFromEnum saleFrom = SaleFromEnum.valueOf(orderOut.getSoFrom());

            switch (saleFrom) {
                case SALE:
                    processSaleOrder(orderOut);
                    break;
                case MARKET:
//                     processMarketOrder(orderOut);
                    break;
                case NG:
//                     processNgOrder(orderOut);
                    break;
                case ZP_ADJUST:
//                     processNgOrder(orderOut);
                    break;
                case CP_ADJUST:
//                     processNgOrder(orderOut);
                    break;
                default:
                    log.warn("[订单处理] 未知订单来源：{}", orderOut.getSoFrom());
                    break;
            }
        } catch (Exception e) {
            log.error("[订单处理] 订单{}处理失败：{}", orderOut.getSoId(), e.getMessage());
        }
    }

    /**
     * 处理销售订单具体逻辑
     * 1. 更新订单物流信息
     * 2. 扣除客户账户余额
     * 3. 更新出库状态
     * 4. 发送余额提醒
     *
     * @param orderOut 出库订单DTO
     */
    private void processSaleOrder(JstOrderOutDTO orderOut) throws Exception {
        OrdersInfoDO orderInfo = ordersInfoService.getOrdersInfoByOrderId(orderOut.getSoId());
        if (orderInfo == null) {
            log.warn("[销售订单] 未找到对应订单：{}", orderOut.getSoId());
            return;
        }

        updateOrderInfo(orderInfo, orderOut);
        BigDecimal balance = deductAccountBalance(orderInfo);
        updateOrderOutStatus(orderOut);
        sendBalanceNotification(orderInfo, balance, orderOut);
    }

    private void updateOrderInfo(OrdersInfoDO orderInfo, JstOrderOutDTO orderOut) {
        orderInfo.setSendTime(LocalDate.now())
                .setSendDate(LocalDate.now())
                .setOrderStatus(OrderStatusType.SHIPPED.getType())
                .setLogisticsNumber(orderOut.getExpress())
                .setLogisticsCompany(orderOut.getExpressCode());
        ordersInfoService.updateOrdersInfoByDO(orderInfo);
    }

    private BigDecimal deductAccountBalance(OrdersInfoDO orderInfo) {
        BigDecimal balance = customerAccountService.saleReceivable(orderInfo);
        log.info("[账户扣款] 订单{}扣款完成，当前余额：{}", orderInfo.getOrderId(), balance);
        return balance;
    }

    private void updateOrderOutStatus(JstOrderOutDTO orderOut) {
        jstOrderOutMapper.update(new LambdaUpdateWrapper<JstOrderOutDO>()
                .eq(JstOrderOutDO::getSoId, orderOut.getSoId())
                .set(JstOrderOutDO::getIsTran, 1));
    }

    private void sendBalanceNotification(OrdersInfoDO orderInfo, BigDecimal balance, JstOrderOutDTO orderOut) throws Exception {
        String userId = dictDataApi.getDictDataLabel("fx_notice_head", String.valueOf(orderInfo.getBusinessBelong()));
        log.info("[余额提醒] 准备发送通知给用户：{}", userId);
        // 钉钉通知实现逻辑...
        String msg = "# 销售单发货提醒：\n### 单号编号:\n" + orderInfo.getOrderId()
                + "\n### 客户名称:" + orderOut.getDisplayName()
                + "\n### 收件人:" + orderOut.getReceiverName()
                + "\n### 发货时间:" + DateUtil.now()
                + "\n### 发货数量：" + orderInfo.getSendQuantity()
                + "\n### 快递公司:" + orderOut.getExpressName()
                + "\n### 快递单号:" + orderOut.getExpress()
                + "\n### 客商余额:" + balance;
        dingTalkUtils.sendNotifyMarkdown("15967343191", "销售单发货提醒", msg);
    }

    private void processImportOrders() {
        List<JstOrderOutDTO> importOrders = jstOrderOutMapper.getImportOrder();
        if (CollectionUtil.isEmpty(importOrders)) {
            log.info("[客商批发] 未找到待处理订单");
            return;
        }

        importOrders.forEach(importOrder -> {
            try {
                importOrderService.updateImportOrderByJstOut(importOrder);
                log.info("[客商批发] 订单{}更新成功", importOrder.getSoId());
            } catch (Exception e) {
                log.error("[客商批发] 订单{}更新失败：{}", importOrder.getSoId(), e.getMessage());
            }
        });
    }

    private void validateExpressInfo(JstOrderOutDTO orderOut, Map<String, String> expressInfo) {
        String expressCode = orderOut.getExpressCode();
        String dictExpressName = expressInfo.get(expressCode);

        if (StrUtil.isBlank(dictExpressName)) {
            log.error("[快递校验] 订单{}快递编码不存在：{}", orderOut.getSoId(), expressCode);
            throw exception(JST_ORDER_OUT_ERROR_EXPRESS_NOT_EXIST, orderOut.getSoId());
        }

        if (StrUtil.isBlank(orderOut.getExpress())) {
            log.error("[快递校验] 订单{}快递单号为空", orderOut.getSoId());
            throw exception(JST_ORDER_OUT_ERROR_EXPRESS_NOT_EXIST, orderOut.getSoId());
        }
    }

}