package cn.iocoder.yudao.module.fx.service.ordersinfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.fx.constant.FieldConstant;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.ProcessInstanceCancelReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder.Ec2jstOrderDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorderitem.Ec2jstOrderitemDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.jushuitanapi.AfterSalesRequest;
import cn.iocoder.yudao.module.fx.dal.dataobject.jushuitanapi.LogisticsRequest;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.module.fx.dal.mysql.ordersdetail.OrdersDetailMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.ordersinfo.OrdersInfoMapper;
import cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.enums.SaleType;
import cn.iocoder.yudao.module.fx.service.bigcustomeraddress.BigCustomerAddressService;
import cn.iocoder.yudao.module.fx.service.customeraccount.CustomerAccountService;
import cn.iocoder.yudao.module.fx.service.customerinfo.CustomerInfoService;
import cn.iocoder.yudao.module.fx.service.ec2jstorder.Ec2jstOrderService;
import cn.iocoder.yudao.module.fx.service.ec2jstorderitem.Ec2jstOrderitemService;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import cn.iocoder.yudao.module.fx.utils.orderinfo.OrderProcessingContext;
import cn.iocoder.yudao.module.fx.utils.orderinfo.template.SaveOrderProcessing;
import cn.iocoder.yudao.module.fx.utils.orderinfo.template.SubmitOrderProcessing;
import cn.iocoder.yudao.module.fx.utils.template.TemplateUtils;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.system.service.dict.DictDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.diboot.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.ORDERS_INFO_NOT_EXISTS;

/**
 * 销售单 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class OrdersInfoServiceImpl implements OrdersInfoService {

    @Resource
    private OrdersInfoMapper ordersInfoMapper;
    @Resource
    private OrdersDetailMapper ordersDetailMapper;
    @Resource
    private BpmProcessInstanceApi processInstanceApi;
    @Resource
    private BigCustomerAddressService bigCustomerAddressService;
    @Resource
    private CustomerInfoService customerInfoService;
    @Resource
    private CustomerAccountService customerAccountService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private DictDataService dictDataService;
    @Lazy
    @Resource
    private Ec2jstOrderService ec2jstOrderService;
    @Resource
    private Ec2jstOrderitemService ec2jstOrderitemService;

    /**
     * 销售单对应的流程定义 KEY
     */
    public static final String PROCESS_KEY = "sale_audit";
    /*
            2 菜鸟义乌仓（ECHANGE）
        38  义乌菜鸟仓
        45  义乌仓(GOODDAY)
        46  义乌仓（德蒂企鹅）
        41  菜鸟义乌仓（VALGER）
     */
    private static final int DEFAULT_SHOP_CODE = 8888;
    private static final Set<String> CAINIAO_WAREHOUSE = new HashSet<>(Arrays.asList("11733495", "12367046", "10790722"));
    private static final Set<String> LOGISTICS_COMPANY_SET = new HashSet<>(Arrays.asList("ZTZS", "OTHER1", "KYE"));
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

    @Override
    public Long saveOrdersInfo(OrdersInfoSaveReqVO saveReqVO) {
        if (saveReqVO == null) {
            throw exception(ErrorCodeConstants.ORDERS_INFO_PARAMS_ERROR);
        }
        // 构建上下文对象
        OrderProcessingContext context = OrderProcessingContext.builder().ordersInfoSaveReqVO(saveReqVO).build();

        // 构建 保存订单的 模板对象
        return TemplateUtils.invokeTemplateMethod(new SaveOrderProcessing(context));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrdersInfo(OrdersInfoSaveReqVO createReqVO) {
        if (createReqVO == null) {
            throw exception(ErrorCodeConstants.ORDERS_INFO_PARAMS_ERROR);
        }
        // 构建上下文对象
        OrderProcessingContext context = OrderProcessingContext.builder().ordersInfoSaveReqVO(createReqVO).build();

        //  构建 提交订单的 模板对象
        Long id = TemplateUtils.invokeTemplateMethod(new SubmitOrderProcessing(context));

        if (id == null) {
            throw exception(ErrorCodeConstants.SYSTEM_ERROR);
        }
        //更新销售单的一些字段
        ordersInfoMapper.updateSaleMain(id);
        String bigCustomerAddress = createReqVO.getBigCustomerAddress();
        if (bigCustomerAddress != null) {
            bigCustomerAddressService.updateBigCustomerAddressCountById(Long.parseLong(bigCustomerAddress));
        }
        //获取当前用户的ID
        Long userId = getLoginUserId();
        log.info("用户ID:{}", userId);
        // 发起 BPM 流程
        Map<String, Object> processInstanceVariables = BeanUtil.beanToMap(createReqVO);
        String processInstanceId = processInstanceApi.createProcessInstance(userId,
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(id)));

        // 将工作流的编号，更新到销售单中
        ordersInfoMapper.updateById(
                new OrdersInfoDO()
                        .setId(id)
                        .setProcessInstanceId(processInstanceId)
                        .setOrderStatus(OrderStatusType.AUDITING.getType()));
        // 返回
        return id;
    }

    /**
     * 创建销售单
     *
     * @return 编号
     */
    @Override
    public Long createOrdersInfoByDO(OrdersInfoDO ordersInfoDO) {
        ordersInfoMapper.insert(ordersInfoDO);
        return ordersInfoDO.getId();
    }

    @Override
    public void updateOrdersInfoStatus(Long id, Integer orderStatusType) {
        validateOrdersInfoExists(id);
        ordersInfoMapper.updateById(new OrdersInfoDO()
                .setId(id)
                .setOrderStatus(orderStatusType));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrdersInfo(OrdersInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateOrdersInfoExists(updateReqVO.getId());
        // 更新
        OrdersInfoDO updateObj = BeanUtils.toBean(updateReqVO, OrdersInfoDO.class);
        ordersInfoMapper.updateById(updateObj);

        // 更新子表
        updateOrdersDetailList(updateReqVO.getId(), updateReqVO.getOrdersDetails());
    }

    /**
     * 更新销售单
     *
     * @param ordersInfoDO
     */
    @Override
    public void updateOrdersInfoByDO(OrdersInfoDO ordersInfoDO) {
        ordersInfoMapper.updateById(ordersInfoDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrdersInfo(Long id) {
        // 校验存在
        validateOrdersInfoExists(id);
        // 删除
        ordersInfoMapper.deleteById(id);

        // 删除子表
        deleteOrdersDetailByOrderId(id);
    }

    /**
     * 校验销售单是否存在
     */
    private OrdersInfoDO validateOrdersInfoExists(Long id) {
        OrdersInfoDO ordersInfoDO = ordersInfoMapper.selectById(id);
        if (ordersInfoDO == null) {
            throw exception(ORDERS_INFO_NOT_EXISTS);
        }
        return ordersInfoDO;
    }

    /**
     * 校验销售单是否存在
     */
    private OrdersInfoDO validateOrdersInfoExists(String OrderId) {
        OrdersInfoDO ordersInfoDO = ordersInfoMapper.selectOne(OrdersInfoDO::getOrderId, OrderId);
        if (ordersInfoDO == null) {
            throw exception(ORDERS_INFO_NOT_EXISTS);
        }
        return ordersInfoDO;
    }

    @Override
    public OrdersInfoDetailRespVO getOrdersInfoById(Long id) {
        OrdersInfoDO ordersInfoDO = validateOrdersInfoExists(id);
        OrdersInfoDetailRespVO respVO = BeanUtils.toBean(ordersInfoDO, OrdersInfoDetailRespVO.class);
        respVO.setOrdersDetails(ordersDetailMapper.selectListByOrderId(id));
        return respVO;
    }

    /**
     * 获得销售单
     *
     * @return 销售单
     */
    @Override
    public OrdersInfoDO getOrdersInfoByOrderId(String orderId) {
        return validateOrdersInfoExists(orderId);
    }

    /**
     * 根据流程编号获得销售单
     *
     * @param processInstanceId 流程编号
     * @return 销售单
     */
    @Override
    public OrdersInfoDetailRespVO getOrdersInfoByPIId(String processInstanceId) {
        OrdersInfoDO ordersInfoDO = ordersInfoMapper.selectOne(new LambdaQueryWrapperX<OrdersInfoDO>().eq(OrdersInfoDO::getProcessInstanceId, processInstanceId));
        if (ordersInfoDO != null) {
            OrdersInfoDetailRespVO respVO = BeanUtils.toBean(ordersInfoDO, OrdersInfoDetailRespVO.class);
            respVO.setOrdersDetails(ordersDetailMapper.selectListByOrderId(ordersInfoDO.getId()));
            return respVO;
        }
        return null;
    }

    @Override
    public PageResult<OrdersInfoDO> getOrdersInfoPage(OrdersInfoPageReqVO pageReqVO) {
        return ordersInfoMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（分销-销售订单明细） ====================

    @Override
    public List<OrdersDetailDO> getOrdersDetailListByOrderId(Long orderId) {
        return ordersDetailMapper.selectListByOrderId(orderId);
    }

    private void createOrdersDetailList(Long orderId, List<OrdersDetailDO> list) {
        //检查所有的商品品牌 是否是同一个品牌
        long count = list.stream().map(OrdersDetailDO::getBrand).distinct().count();
        if (count > 1) {
            throw exception(ErrorCodeConstants.ORDERS_DETAIL_BRAND_NOT_SAME);
        }
        list.forEach(o -> {
            Long id = o.getId();
            o.setGoodsId(id);
            o.setId(null);
            o.setOrderId(orderId);
        });
        ordersDetailMapper.insertBatch(list);
    }

    private void updateOrdersDetailList(Long orderId, List<OrdersDetailDO> list) {
        deleteOrdersDetailByOrderId(orderId);
        list.forEach(o -> o.setId(null).setUpdater(null).setUpdateTime(null)); // 解决更新情况下：1）id 冲突；2）updateTime 不更新
        createOrdersDetailList(orderId, list);
    }

    private void deleteOrdersDetailByOrderId(Long orderId) {
        ordersDetailMapper.deleteByOrderId(orderId);
    }

    @Override
    public void cancelProcessInstance(Long loginUserId, ProcessInstanceCancelReqVO cancelReqVO) {
        processInstanceApi.cancelProcessInstance(getLoginUserId(), cancelReqVO.getId(), cancelReqVO.getReason());
        log.info("用户:{} 取消流程实例:{}", loginUserId, cancelReqVO.getId());
        // 更新此流程中所有代办的状态 TODO
    }

    @Override
    public List<OrdersInfoDO> getUnUploadedOrders() {
        List<OrdersInfoDO> orders = ordersInfoMapper.selectList(new LambdaQueryWrapper<OrdersInfoDO>().eq(OrdersInfoDO::getIsToErp, FieldConstant.IS_TO_ERP_UNPROCESSED));
        return CollectionUtil.isEmpty(orders) ? Collections.emptyList() : orders;
    }

    @Override
    public void saleProcess() {
        List<OrdersInfoDO> allOrders = Stream.concat(
                ordersInfoMapper.getCrossBorderOrders().stream(), // 跨境订单
                ordersInfoMapper.selectList(new LambdaQueryWrapper<OrdersInfoDO>() //仅退款单据直接发货扣款，用于退差
                                .eq(OrdersInfoDO::getOrderStatus, OrderStatusType.WAITING_FOR_ERP)
                                .eq(OrdersInfoDO::getSalesType, SaleType.ONLY_REFUND))
                        .stream()
        ).collect(Collectors.toList());

        allOrders.forEach(o -> {
            try {
                //置为已发货
                o.setOrderStatus(OrderStatusType.SHIPPED.getType());
                o.setSendDate(LocalDate.now());
                o.setSendTime(LocalDateTime.now());
                //自动扣款，并且自动生成账户调整记录【类型为扣款】
                customerAccountService.saleReceivable(o);
            } catch (Exception e) {
                log.error("[saleProcess]订单处理失败 ID:{}", o.getId(), e);
                throw new RuntimeException(e);
            }
        });
        Map<String, String> logisticsCompanyMap = getLogisticsCompanyMap();
        String bToB = StrUtil.EMPTY;
        List<OrdersInfoDO> normalSendOrders = ordersInfoMapper.getNormalSendOrders();
        for (OrdersInfoDO order : normalSendOrders) {
            /*
                义乌订单根据快递方式做调整：
                1.选择“自提”或者“跨越”传2B订单给仓库
                2.选择其他快递默认传2C订单，快递方式为（中通）
                增加逻辑
                淮安仓以及淮安仓（valger） 默认其他other
             */
            String warehouseCode = order.getWarehouseCode();
            // 物流公司调整策略
            String logisticsCompany = adjustLogisticsCompany(order);
            if (CAINIAO_WAREHOUSE.contains(warehouseCode) && LOGISTICS_COMPANY_SET.contains(logisticsCompany)) {
                //2B判断
                bToB = "菜鸟大B2B";
            }
            // 构建订单备注
            String remark = buildOrderRemark(order);

            // 确定店铺编码
            Integer storeCode = determineStoreCode(order);

            //组合订单标签
            String orderLabel = Stream.of(order.getWarehouse(), bToB, order.getBrand())
                    .filter(StrUtil::isNotBlank)
                    .collect(Collectors.joining(","));
            CustomerInfoDO customerInfo = customerInfoService.getCustomerInfo(order.getDistributorId());
            //插入订单表
            Integer ec2jstOrderId = createJstOrder(order, storeCode, customerInfo, remark, logisticsCompanyMap, logisticsCompany, orderLabel);
            // 批量处理订单明细
            List<Ec2jstOrderitemDO> detailList = ordersDetailMapper.selectListByOrderId(order.getId())
                    .stream()
                    .map(o -> new Ec2jstOrderitemDO().setShopSkuId(o.getSkuId())
                            .setSkuiId(o.getSkuId())
                            .setPropertiesValue(o.getCategory())
                            .setAmount(o.getPriceAmount())
                            .setBasePrice(o.getSalePrice())
                            .setQty(BigDecimal.valueOf(o.getCount()))
                            .setName(o.getSkuName())
                            .setMainid(ec2jstOrderId))
                    .collect(Collectors.toList());
            ec2jstOrderitemService.saveEc2jstOrderItemList(detailList);
            //更新销售单状态
            ordersInfoMapper.updateById(new OrdersInfoDO()
                    .setId(order.getId())
                    .setOrderStatus(OrderStatusType.EXTERNAL_SHIPPING.getType())
                    .setIsToErp(1)
                    .setToErpTime(LocalDateTime.now()));
        }
        //上传订单
        ec2jstOrderService.uploadOrders();
    }

    private Integer createJstOrder(OrdersInfoDO order, Integer storeCode, CustomerInfoDO customerInfo, String remark, Map<String, String> logisticsCompanyMap, String logisticsCompany, String orderLabel) {
        Ec2jstOrderDO insertDO = Ec2jstOrderDO.builder()
//                .shopId(storeCode)
                .shopId(18061827)
                .orderDate(String.valueOf(order.getOrderDate()))
                .shopStatus("WAIT_SELLER_SEND_GOODS")
                .shopBuyerId(customerInfo.getDistributorName())
                .receiverState(order.getProvince())
                .receiverCity(order.getCity())
                .receiverDistrict(order.getDistrict())
                .receiverAddress(order.getAddress())
                .receiverName(order.getManager())
                .receiverPhone(order.getPhone())
                .payAmount(order.getSalesAmount())
                .remark(remark)
                .shopModified(DateUtil.now())
                .logisticsCompany(logisticsCompanyMap.get(logisticsCompany))
                .sellerFlag("分销订单" + order.getOrderId())
                .lcId(logisticsCompany)
                .orderNo(order.getOrderId())
                .warehouse(orderLabel).build();
        return ec2jstOrderService.createEc2jstOrderByDO(insertDO);
    }

    private Integer validateShopCode(Integer businessBelong) {
        return Optional.ofNullable(SHOP_CODE_MAPPING.get(businessBelong))
                .filter(code -> code != DEFAULT_SHOP_CODE)
                .orElseThrow(() -> new BusinessException(StrUtil.format("原销售单业务归属[{}]对应店铺编码不存在", businessBelong)));
    }

    /**
     * 带缓存的物流公司字典获取
     */
    private Map<String, String> getLogisticsCompanyMap() {
        return Optional.ofNullable(stringRedisTemplate.opsForValue().get("dictTypefx_wl"))
                .map(json -> JSONUtil.toList(json, DictDataDO.class))
                .orElseGet(() -> {
                    List<DictDataDO> dbList = dictDataService.getDictDataListByDictType("fx_wl");
                    stringRedisTemplate.opsForValue().set("dictTypefx_wl", JSONUtil.toJsonStr(dbList));
                    return dbList;
                })
                .stream()
                .collect(Collectors.toMap(
                        DictDataDO::getLabel,
                        DictDataDO::getValue,
                        (existing, replacement) -> {
                            log.warn("物流公司重复标签: {}", existing);
                            return existing;
                        }
                ));
    }

    private String adjustLogisticsCompany(OrdersInfoDO order) {
        String logisticsCompany = order.getLogisticsCompany();
        if ("11717238".equals(order.getWarehouseCode()) && order.getCusDfType() == 1) {
            switch (logisticsCompany) {
                case "STO":
                    return "STO.1";
                case "YTO":
                    return "YTO.1";
                default:
                    return "ZTO.1";
            }
        }
        return logisticsCompany;
    }

    private String buildOrderRemark(OrdersInfoDO order) {
        StringBuilder remarkBuilder = new StringBuilder();
        if (order.getIsTraceless() == 1) {
            remarkBuilder.append("（无痕发货）");
        }
        if ("需要扫溯源码出库".equals(order.getRequirement())) {
            remarkBuilder.append(remarkBuilder.length() > 0 ? "，" : "").append("需要扫溯源码出库");
        }
        StringBuilder append = remarkBuilder.append(order.getRemark());
        return append.length() > 0 ? append.toString() : StrUtil.EMPTY;
    }

    private Integer determineStoreCode(OrdersInfoDO order) {
        if (order.getIsTraceless() == 1) {
            return 15188159;
        }
        if (order.getSendQuantity() >= 20 && CAINIAO_WAREHOUSE.contains(order.getWarehouseCode())) {
            return 16815847;
        }
        return validateShopCode(order.getBusinessBelong());
    }

    /**
     * 检查商品中是否存在小样
     *
     * @return
     */
    @Override
    public Boolean checkSample(Long id) {
        Integer count = ordersInfoMapper.checkSample(id);
        return count > 0;
    }

    /**
     * 检查商品是否满足箱规
     *
     * @param id
     * @return
     */
    @Override
    public Boolean checkBoxSize(Long id) {
        return ordersInfoMapper.checkBoxSize(id) > 0;
    }

    /**
     * 获取自动发货的订单
     */
    @Override
    public List<OrdersInfoDO> getAutoSendOrders() {
        List<OrdersInfoDO> autoSendOrders = ordersInfoMapper.getAutoSendOrders();
        return CollectionUtil.emptyToDefault(autoSendOrders);
    }

    /**
     * 获得待退货销售单分页
     *
     * @param pageReqVO 分页查询
     * @return 销售单分页
     */
    @Override
    public PageResult<OrdersInfoDO> getReturnOrdersInfoPage(OrdersInfoPageReqVO pageReqVO) {
        return ordersInfoMapper.selectReturnPage(pageReqVO);
    }

    /**
     * 用户创建流程实例
     */
    @Override
    public void startProcessInstance(Long loginUserId, Long id) {
        OrdersInfoDetailRespVO ordersInfoById = getOrdersInfoById(id);
        // 发起 BPM 流程
        Map<String, Object> processInstanceVariables = BeanUtil.beanToMap(ordersInfoById);
        String processInstanceId = processInstanceApi.createProcessInstance(loginUserId,
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(id)));

        // 将工作流的编号，更新到销售单中
        ordersInfoMapper.updateById(
                new OrdersInfoDO()
                        .setId(id)
                        .setProcessInstanceId(processInstanceId)
                        .setOrderStatus(OrderStatusType.AUDITING.getType()));
    }

    /**
     * 聚水潭回调物流同步
     *
     * @return
     */
    @Override
    public Boolean processLogisticsSync(LogisticsRequest logisticsRequest) {
        OrdersInfoDO ordersInfo = validateOrdersInfoExists(logisticsRequest.getOId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ordersInfo.setLogisticsCompany(logisticsRequest.getLcId())
                .setLogisticsNumber(logisticsRequest.getLId())
                .setOrderStatus(OrderStatusType.SHIPPED.getType())
                .setErpOrderNumber(String.valueOf(logisticsRequest.getOId()))
                .setSendDate(LocalDate.parse(logisticsRequest.getSendDate(), formatter))
                .setSendTime(LocalDateTime.parse(logisticsRequest.getSendDate(), formatter));
        ordersInfoMapper.updateById(ordersInfo);
        return true;
    }

    /**
     * 聚水潭回调售后同步
     */
    @Override
    public void processAfterSalesSync(AfterSalesRequest afterSalesRequest) {
        OrdersInfoDO ordersInfo = validateOrdersInfoExists(afterSalesRequest.getOId());
    }

}