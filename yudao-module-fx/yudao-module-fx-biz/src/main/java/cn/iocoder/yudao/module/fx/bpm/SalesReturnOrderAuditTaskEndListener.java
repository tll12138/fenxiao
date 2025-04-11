package cn.iocoder.yudao.module.fx.bpm;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.utils.SpringUtil;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale.JstAfterSaleDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersaledata.JstAfterSaleDataDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorderdetail.ReturnOrderDetailDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository.SendRepositoryDO;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.service.bizerrorlog.BizErrorLogService;
import cn.iocoder.yudao.module.fx.service.customeraccount.CustomerAccountService;
import cn.iocoder.yudao.module.fx.service.jstaftersale.JstAfterSaleService;
import cn.iocoder.yudao.module.fx.service.jstaftersaledata.JstAfterSaleDataService;
import cn.iocoder.yudao.module.fx.service.ordersdetail.OrdersDetailService;
import cn.iocoder.yudao.module.fx.service.ordersinfo.OrdersInfoService;
import cn.iocoder.yudao.module.fx.service.returnorder.ReturnOrderService;
import cn.iocoder.yudao.module.fx.utils.BigDecimalUtils;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import cn.iocoder.yudao.module.system.api.dict.DictDataApi;
import cn.iocoder.yudao.module.system.util.dd.DingTalkUtils;
import com.diboot.core.exception.BusinessException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tll
 * @date 2025-04-08 15:04:45
 */

/**
 * 退货单归档后动作
 * 2B走售后上传 /open/aftersale/upload
 * 2C走新建其它出入库 /open/jushuitan/otherinout/upload
 */
@Component("salesReturnOrderAuditTaskEndListener")
@Slf4j
public class SalesReturnOrderAuditTaskEndListener {
    private static final String LOG_MODULE = "fx";
    private static final String LOG_TYPE = "SalesReturnOrderAuditTaskEndListener";
    private static final Map<Integer, Integer> SHOP_CODE_MAPPING;

    static {
        Map<Integer, Integer> tempMap = new HashMap<>();
        tempMap.put(0, 12756612);
        tempMap.put(1, 12756618);
        tempMap.put(2, 12756620);
        tempMap.put(4, 14721262);
        tempMap.put(5, 14825041);
        tempMap.put(6, 15143159);
        tempMap.put(7, 16273023);
        tempMap.put(8, 16474604);
        tempMap.put(9, 16803768);
        SHOP_CODE_MAPPING = Collections.unmodifiableMap(tempMap);
    }

    public void execute(DelegateExecution execution) {
        ReturnOrderService returnOrderService = SpringUtil.getObject(ReturnOrderService.class);
        OrdersInfoService ordersInfoService = SpringUtil.getObject(OrdersInfoService.class);
        OrdersDetailService ordersDetailService = SpringUtil.getObject(OrdersDetailService.class);
        DictDataApi dictDataApi = SpringUtil.getObject(DictDataApi.class);
        StringRedisTemplate stringRedisTemplate = SpringUtil.getObject(StringRedisTemplate.class);
        JstAfterSaleService afterSaleService = SpringUtil.getObject(JstAfterSaleService.class);
        JstAfterSaleDataService afterSaleDataService = SpringUtil.getObject(JstAfterSaleDataService.class);
        CustomerAccountService accountService = SpringUtil.getObject(CustomerAccountService.class);
        final String processId = execution.getProcessInstanceId();
        String errorMsg = StrUtil.EMPTY;
        try {
            ReturnOrdersInfoDetailRespVO returnOrder = validateReturnOrder(processId, returnOrderService);
            //一个销售单只会有一个品牌
            List<ReturnOrderDetailDO> returnOrdersDetails = returnOrder.getOrdersDetails();
            //原销售单编号
            String originOrderId = returnOrder.getOriginOrder();
            //收货经销商
            int receiveDealer = returnOrder.getReceiveDealer().intValue();
            //退货类型
            Integer returnBusinessType = returnOrder.getReturnBusinessType();
            Integer returnType = returnOrder.getReturnType();
            OrdersInfoDO ordersInfo = ordersInfoService.getOrdersInfoByOrderId(originOrderId);
            List<OrdersDetailDO> ordersDetailDOList = ordersDetailService.getOrdersDetailByOrderId(ordersInfo.getId());
            List<OrdersDetailDO> saveDetailList = Lists.newArrayList();
            //找到原平台单号对应下单店铺,排除4内部仓，走另外逻辑
            if (!Lists.newArrayList(10, 22, 23, 24, 33, 34).contains(receiveDealer) && !"蒲岐内部仓".equals(returnOrder.getWarehouse())) {
                Integer businessBelong = ordersInfo.getBusinessBelong();
                int shopCode = SHOP_CODE_MAPPING.getOrDefault(businessBelong, 8888);
                if (shopCode == 8888) {
                    throw new BusinessException("原销售单的下单店铺为空！请检查!");
                }
                String repositoryJson = stringRedisTemplate.opsForValue().get("repository:info:mapping");
                int channel = 2;
                if (StrUtil.isNotBlank(repositoryJson)) {
                    String warehouseCode = returnOrder.getWarehouseCode();
                    if (StrUtil.isBlank(warehouseCode)) {
                        throw new BusinessException("收货仓库为空！请检查!");
                    }
                    Map<String, SendRepositoryDO> sendRepositoryMap = JSONUtil.parseObj(repositoryJson).toBean(Map.class);
                    SendRepositoryDO sendRepositoryDO = sendRepositoryMap.get(warehouseCode);
                    channel = sendRepositoryDO.getChannel();
                }
                //更新销售单退货标记与销售单明细退货数量
                String logisticsCompanyName = dictDataApi.getDictDataLabel("fx_wl", returnOrder.getLogisticsCompany());
                Map<String, OrdersDetailDO> skuDetailMap = ordersDetailDOList.stream()
                        .collect(Collectors.toMap(
                                OrdersDetailDO::getSkuId,
                                Function.identity(),
                                (oldVal, newVal) -> newVal));
                if (returnBusinessType == 0 || channel == 1 || channel == 2) {
                    //2C的推单逻辑
                    BigDecimal refund = BigDecimal.ZERO;
                    List<JstAfterSaleDataDO> detailList = Lists.newArrayList();
                    for (ReturnOrderDetailDO detail : returnOrdersDetails) {
                        detailList.add(new JstAfterSaleDataDO(
                                returnOrder.getOrderId()
                                , detail.getSkuId()
                                , detail.getCount()
                                , detail.getSaleAmt()
                                , detail.getSkuName()
                                , detail.getCategory()));
                        refund = BigDecimalUtils.add(refund, detail.getSaleAmt());
                        OrdersDetailDO ordersDetailDO = skuDetailMap.get(detail.getSkuId());
                        ordersDetailDO.setReturnFlag("1");
                        ordersDetailDO.setReturnCount(Optional.ofNullable(ordersDetailDO.getReturnCount()).orElse(0) + detail.getCount());
                        saveDetailList.add(ordersDetailDO);
                    }
                    //插入主表
                    Long mainId = afterSaleService.createJstAfterSale(new JstAfterSaleDO(returnOrder.getOrderId()
                            , originOrderId
                            , logisticsCompanyName
                            , returnOrder.getLogisticsNumber()
                            , StrUtil.format("{}销售单{}退货，原内部单号：{}", returnOrder.getRemark(), originOrderId, ordersInfo.getErpOrderNumber())
                            , ordersInfo.getSalesAmount()
                            , Long.valueOf(returnOrder.getWarehouseCode().trim())
                            , "0".equals(returnOrder.getWarehouseFeature()) ? 1 : 2
                            , refund
                    ));
                    detailList.forEach(item -> item.setMainId(mainId));
                    afterSaleDataService.saveBatch(detailList);
                } else if (returnBusinessType == 1) {
                    //插入主表
                    Long mainId = afterSaleService.createJstAfterSale(new JstAfterSaleDO(returnOrder.getOrderId()
                            , "0".equals(returnOrder.getWarehouseFeature()) ? 1 : 4
                            , Long.valueOf(returnOrder.getWarehouseCode().trim())
                            , returnOrder.getLogisticsCompany()
                            , returnOrder.getLogisticsNumber()
                            , logisticsCompanyName
                    ));
                    List<JstAfterSaleDataDO> detailList = Lists.newArrayList();
                    for (ReturnOrderDetailDO detail : returnOrdersDetails) {
                        detailList.add(new JstAfterSaleDataDO(mainId
                                , detail.getSkuId()
                                , detail.getCount()
                                , detail.getSaleAmt()));
                        OrdersDetailDO ordersDetailDO = skuDetailMap.get(detail.getSkuId());
                        ordersDetailDO.setReturnFlag("1");
                        ordersDetailDO.setReturnCount(ordersDetailDO.getReturnCount() + detail.getCount());
                        saveDetailList.add(ordersDetailDO);
                    }
                    afterSaleDataService.saveBatch(detailList);
                } else {
                    throw new BusinessException(StrUtil.format("不存在该退货业务类型：{}！请检查!", returnBusinessType));
                }
                ordersDetailService.updateBatchById(saveDetailList);
            }
            //可退数量为0的行是否等于总行数
            boolean isAllReturned = saveDetailList.stream()
                    .allMatch(detail ->
                            Objects.equals(detail.getCount(), detail.getReturnCount())
                    );
            ordersInfo.setReturnStatus(isAllReturned ? 2 : 1);

            ordersInfoService.updateOrdersInfoByDO(ordersInfo);
            //标记退货单已转换，单据状态为ERP收货中
            returnOrder.setIsToErp(1).setOrderStatus(8).setToErpTime(LocalDate.now());
            returnOrderService.updateReturnOrderByTran(returnOrder);

            if (returnType == 4 || returnType == 5) {
                String newSoId = generateNewSoId(originOrderId, returnBusinessType);
                //创建新销售单
                OrdersInfoDO newOrderInfo = createNewSale(ordersInfo, returnOrder, newSoId);
                Long newMainId = ordersInfoService.createOrdersInfoByDO(newOrderInfo);
                //创建新销售单明细
                List<OrdersDetailDO> details = returnOrdersDetails.stream()
                        .map(detail -> createOrderDetail(detail, newMainId))
                        .collect(Collectors.toList());
                ordersDetailService.saveBatch(details);
                // 新增：汇总销售金额和数量
                BigDecimal totalSaleAmt = details.stream()
                        .map(OrdersDetailDO::getSaleAmount)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                int totalCount = details.stream()
                        .mapToInt(d -> Optional.ofNullable(d.getCount()).orElse(0))
                        .sum();
                //更新汇总字段
                newOrderInfo.setSalesAmount(totalSaleAmt);
                newOrderInfo.setSendQuantity(totalCount);
                ordersInfoService.updateOrdersInfoByDO(newOrderInfo);
            }

            //调用退款账户调整的程序，返还客户账户余额
            accountService.resaleReceivable(returnOrder);

            //发送钉钉消息
//            if (returnBusinessType == 1) {
            //消息通知给商品yyt,根据原单品牌判断且只用发2C
            this.sendNotification("18768338906", returnOrder, dictDataApi);
//            }

            //TODO 调用聚水潭api统一执行退货推送

        } catch (Exception e) {
            errorMsg = StrUtil.format("[退货单处理失败] processId:{},原因：{}", processId, e.getMessage());
            log.error(errorMsg, e);
            throw new BusinessException(errorMsg);
        } finally {
            logErrorIfNeeded(processId, errorMsg);
        }
    }

    /**
     * 订单基础校验
     *
     * @param processInstanceId 流程实例ID
     * @return 有效订单信息
     * @throws BusinessException 当订单不存在或状态异常时抛出
     */
    private ReturnOrdersInfoDetailRespVO validateReturnOrder(String processInstanceId, ReturnOrderService returnOrderService) {
        ReturnOrdersInfoDetailRespVO returnOrderDO = returnOrderService.getReturnOrderByProcessId(processInstanceId);
        // 订单存在性校验
        if (returnOrderDO == null) {
            throw new BusinessException("流程对应的退货单不存在");
        }
        // 订单状态校验（防止重复操作）
        if (OrderStatusType.WAITING_FOR_ERP.getType().equals(returnOrderDO.getOrderStatus())) {
            throw new BusinessException("退货单已执行，无法重复操作");
        }
        //判断数量不为0的明细是否为空
        if (CollectionUtil.isEmpty(returnOrderDO.getOrdersDetails())) {
            throw new BusinessException("不存在退货明细,请检查!");
        }
        return returnOrderDO;
    }

    private void logErrorIfNeeded(String processId, String errorMsg) {
        if (StrUtil.isNotBlank(errorMsg)) {
            BizErrorLogService bizErrorLogService = SpringUtil.getObject(BizErrorLogService.class);
            bizErrorLogService.createBizErrorLog(LOG_MODULE, LOG_TYPE, processId, null, errorMsg);
        }
    }


    /**
     * 生成唯一销售单号
     *
     * @param originSoId 原销售单号
     * @param retType    退货类型
     */
    private String generateNewSoId(String originSoId, Integer retType) {
        String suffix = (retType == 4) ?
                "-H" + RandomUtil.randomInt(100, 1000) :
                "-J" + RandomUtil.randomInt(100, 1000);
        return originSoId + suffix;
    }

    /**
     * 创建新销售单
     * 退货类型等于换货,插入换货类型的销售单，类型值为1
     * 退货类型等于仅退款,插入仅退款类型的销售单，类型值为5
     */
    private OrdersInfoDO createNewSale(OrdersInfoDO ordersInfo, ReturnOrdersInfoDetailRespVO returnOrder, String newSoId) {
        OrdersInfoDO ordersInfoDO = new OrdersInfoDO();
        // 复制原单信息
        BeanUtils.copyProperties(ordersInfo, ordersInfoDO, "id", "returnNo", "returnStatus");
        // 公共字段
        ordersInfoDO.setOrderDate(LocalDate.now());
        ordersInfoDO.setOrderStatus(0);
        ordersInfoDO.setSalesType(returnOrder.getReturnType() == 4 ? 1 : 3); // 1:换货 3:仅退款
        ordersInfoDO.setRemark(returnOrder.getReturnType() == 4 ?
                "由销售退货单" + returnOrder.getOrderId() + "换货生成" :
                "由销售退货单" + returnOrder.getOrderId() + "仅退款退差价生成");
        ordersInfoDO.setOrderId(newSoId);
        return ordersInfoDO;
    }

    /**
     * 创建销售明细实体
     */
    private OrdersDetailDO createOrderDetail(ReturnOrderDetailDO source, Long saleId) {
        OrdersDetailDO detail = new OrdersDetailDO();
        detail.setCategory(source.getCategory());
        detail.setSalePrice(source.getCostPrice());
        detail.setSaleAmount(source.getSaleAmt());
        detail.setSkuId(source.getSkuId());
        detail.setSkuName(source.getSkuName());
        detail.setCount(source.getCount());
        detail.setOrderId(saleId);
        detail.setReturnCount(0);
        detail.setReturnFlag("0");
        return detail;
    }


    private void sendNotification(String userId, ReturnOrdersInfoDetailRespVO returnOrder, DictDataApi dictDataApi) throws Exception {
        DingTalkUtils dingTalkUtils = SpringUtil.getObject(DingTalkUtils.class);
        log.info("[退货提醒] 准备发送通知给用户：{}", userId);
        // 钉钉通知实现逻辑...
        String msg = "# 客商退货提醒：\n### 客户名称:\n" + returnOrder.getReturnUserName()
                + "\n### 单据编号:" + returnOrder.getOrderId()
                + "\n### 退货数量:" + returnOrder.getTotalReturnQuantity()
                + "\n### 退货原单:" + returnOrder.getOriginOrder()
                + "\n### 请确认聚水潭售后单的相关信息！";
        dingTalkUtils.sendNotifyMarkdown("15967343191", "客商退换货提醒", msg);
    }
}
