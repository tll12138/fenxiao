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
import cn.iocoder.yudao.module.system.api.dict.DictDataApi;
import cn.iocoder.yudao.module.system.util.dd.DingTalkUtils;
import com.diboot.core.exception.BusinessException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
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
    private static final Set<Integer> SPECIAL_DEALERS = new HashSet<>(Arrays.asList(10, 22, 23, 24, 33, 34));
    private static final String INTERNAL_WAREHOUSE = "蒲岐内部仓";
    private static final int DEFAULT_SHOP_CODE = 8888;
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

    @Transactional(rollbackFor = Exception.class)
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
            List<ReturnOrderDetailDO> returnOrdersDetails = returnOrder.getOrdersDetails();
            // 使用 Optional 处理原单号
            String originOrderId = Optional.ofNullable(returnOrder.getOriginOrder())
                    .filter(StrUtil::isNotBlank)
                    .orElseThrow(() -> new BusinessException("原销售单流程编号为空"));
            //收货经销商
            int receiveDealer = returnOrder.getReceiveDealer().intValue();
            //退货类型
            Integer returnBusinessType = returnOrder.getReturnBusinessType();
            Integer returnType = returnOrder.getReturnType();
            OrdersInfoDO ordersInfo = ordersInfoService.getOrdersInfoByOrderId(originOrderId);
            List<OrdersDetailDO> ordersDetailDOList = ordersDetailService.getOrdersDetailByOrderId(ordersInfo.getId());
            List<OrdersDetailDO> saveDetailList = Lists.newArrayList();
            //找到原平台单号对应下单店铺,排除4内部仓，走另外逻辑
            if (!SPECIAL_DEALERS.contains(receiveDealer) && !INTERNAL_WAREHOUSE.equals(returnOrder.getWarehouse())) {
                //校验原销售单业务归属
                Integer businessBelong = Optional.ofNullable(ordersInfo.getBusinessBelong())
                        .orElseThrow(() -> new BusinessException("原销售单业务归属不能为空"));
                //校验店铺编码
                validateShopCode(businessBelong);

                //获取仓库特征
                int channel = Optional.ofNullable(stringRedisTemplate.opsForValue().get("repository:info:mapping"))
                        .map(json -> parseChannelFromRepositoryJson(json, returnOrder))
                        .orElse(2);
                //物流公司名称查询
                String logisticsCompanyName = Optional.ofNullable(dictDataApi.getDictDataLabel("fx_wl", returnOrder.getLogisticsCompany()))
                        .orElseThrow(() -> new BusinessException(StrUtil.format("物流公司信息不存在，编码：{}", returnOrder.getLogisticsCompany())));
                //更新销售单退货标记与销售单明细退货数量
                Map<String, OrdersDetailDO> skuDetailMap = createSkuDetailMap(ordersDetailDOList);
                List<JstAfterSaleDataDO> detailList;
                Long mainId;
                // 统一主表创建逻辑
                JstAfterSaleDO.JstAfterSaleDOBuilder mainBuilder = createMainBuilder(returnOrder, logisticsCompanyName)
                        .warehouseType("0".equals(returnOrder.getWarehouseFeature()) ? 1 : 2);
                if (returnBusinessType == 0 || channel == 1 || channel == 2) {
                    //2C的推单逻辑
                    detailList = processDetails(returnOrdersDetails, skuDetailMap, saveDetailList,
                            (detail, orderDetail) -> JstAfterSaleDataDO.builder()
                                    .outerOiId(returnOrder.getOrderId())
                                    .skuId(detail.getSkuId())
                                    .qty(detail.getCount())
                                    .amount(detail.getSaleAmt())
                                    .name(orderDetail.getSkuName())  // 使用orderDetail更可靠
                                    .propertiesValue(orderDetail.getCategory())
                                    .build());
                    BigDecimal refund = detailList.stream()
                            .map(JstAfterSaleDataDO::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    //插入主表
                    mainId = afterSaleService.createJstAfterSale(createMainBuilder(returnOrder, logisticsCompanyName)
                            .soId(ordersInfo.getErpOrderNumber())
                            .remark(StrUtil.format("{}销售单{}退货，原内部单号：{}", returnOrder.getRemark(), originOrderId, ordersInfo.getErpOrderNumber()))
                            .totalAmount(ordersInfo.getSalesAmount())
                            .warehouseType("0".equals(returnOrder.getWarehouseFeature()) ? 1 : 2)
                            .refund(refund)
                            .sourceType("2C")
                            .build());
                    detailList.forEach(item -> item.setMainId(mainId));
                    afterSaleDataService.saveBatch(detailList);
                } else if (returnBusinessType == 1) {
                    //插入主表
                    mainId = afterSaleService.createJstAfterSale(createMainBuilder(returnOrder, logisticsCompanyName)
                            .warehouse("0".equals(returnOrder.getWarehouseFeature()) ? 1 : 4)
                            .lcId(returnOrder.getLogisticsCompany())
                            .sourceType("2B")
                            .build());
                    detailList = processDetails(returnOrdersDetails, skuDetailMap, saveDetailList,
                            (detail, orderDetail) -> JstAfterSaleDataDO.builder()
                                    .mainId(mainId)
                                    .qty(detail.getCount())
                                    .salePrice(detail.getSaleAmt())
                                    .skuId(detail.getSkuId())
                                    .build());
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
     */
    private ReturnOrdersInfoDetailRespVO validateReturnOrder(String processInstanceId, ReturnOrderService returnOrderService) {
        return Optional.ofNullable(returnOrderService.getReturnOrderByProcessId(processInstanceId))
                .map(vo -> {
                    Optional.of(vo.getOrderStatus())
                            .filter(status -> OrderStatusType.WAITING_FOR_ERP.getType().equals(status))
                            .ifPresent(s -> {
                                throw new BusinessException("退货单已执行，无法重复操作");
                            });

                    Optional.ofNullable(vo.getOrdersDetails())
                            .filter(list -> !list.isEmpty())
                            .orElseThrow(() -> new BusinessException("不存在退货明细"));

                    return vo;
                })
                .orElseThrow(() -> new BusinessException("流程对应的退货单不存在"));
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

    // 处理SKU校验及明细更新
    private OrdersDetailDO processSkuDetail(ReturnOrderDetailDO detail,
                                            Map<String, OrdersDetailDO> skuDetailMap,
                                            List<OrdersDetailDO> saveDetailList) {
        OrdersDetailDO orderDetail = Optional.ofNullable(skuDetailMap.get(detail.getSkuId()))
                .orElseThrow(() -> new BusinessException(StrUtil.format("SKU[{}]不存在", detail.getSkuId())));
        orderDetail.setReturnFlag("1");
        orderDetail.setReturnCount(Optional.ofNullable(orderDetail.getReturnCount()).orElse(0) + detail.getCount());
        saveDetailList.add(orderDetail);
        return orderDetail;
    }

    // 处理明细生成逻辑
    private List<JstAfterSaleDataDO> processDetails(List<ReturnOrderDetailDO> returnOrdersDetails,
                                                    Map<String, OrdersDetailDO> skuDetailMap,
                                                    List<OrdersDetailDO> saveDetailList,
                                                    BiFunction<ReturnOrderDetailDO, OrdersDetailDO, JstAfterSaleDataDO> builder) {
        return returnOrdersDetails.stream()
                .map(detail -> {
                    OrdersDetailDO orderDetail = processSkuDetail(detail, skuDetailMap, saveDetailList);
                    return builder.apply(detail, orderDetail);
                })
                .collect(Collectors.toList());
    }

    private JstAfterSaleDO.JstAfterSaleDOBuilder createMainBuilder(ReturnOrdersInfoDetailRespVO returnOrder, String logisticsCompanyName) {
        return JstAfterSaleDO.builder()
                .outerAsId(returnOrder.getOrderId())
                .wmsCoId(Long.valueOf(returnOrder.getWarehouseCode().trim()))
                .lId(returnOrder.getLogisticsNumber())
                .logisticsCompany(logisticsCompanyName);
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

    private void validateShopCode(Integer businessBelong) {
        Optional.ofNullable(SHOP_CODE_MAPPING.get(businessBelong))
                .filter(code -> code != DEFAULT_SHOP_CODE)
                .orElseThrow(() -> new BusinessException(StrUtil.format("原销售单业务归属[{}]对应店铺编码不存在", businessBelong)));
    }

    private int parseChannelFromRepositoryJson(String repositoryJson, ReturnOrdersInfoDetailRespVO returnOrder) {
        return Optional.ofNullable(returnOrder.getWarehouseCode())
                .filter(StrUtil::isNotBlank)
                .map(warehouseCode -> {
                    Map<String, SendRepositoryDO> sendRepositoryMap = JSONUtil.parseObj(repositoryJson).toBean(Map.class);
                    return Optional.ofNullable(sendRepositoryMap.get(warehouseCode))
                            .map(SendRepositoryDO::getChannel)
                            .orElse(2);
                })
                .orElseThrow(() -> new BusinessException("收货仓库编码不能为空"));
    }

    private Map<String, OrdersDetailDO> createSkuDetailMap(List<OrdersDetailDO> ordersDetailDOList) {
        return ordersDetailDOList.stream()
                .collect(Collectors.toMap(
                        OrdersDetailDO::getSkuId,
                        Function.identity(),
                        (existing, replacement) -> existing));
    }

}
