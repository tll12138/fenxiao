package cn.iocoder.yudao.module.fx.utils.orderinfo.template;

import cn.hutool.core.lang.Opt;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.convert.customeraddress.CustomerAddressConvert;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.module.fx.enums.OrderNumPrefixType;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.utils.orderinfo.OrderProcessingContext;
import cn.iocoder.yudao.module.fx.utils.validate.BrandValidationHandler;
import cn.iocoder.yudao.module.fx.utils.validate.HandleChainBuilder;
import cn.iocoder.yudao.module.fx.utils.validate.InventoryValidationHandler;
import cn.iocoder.yudao.module.fx.utils.validate.ValidationHandler;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class SaveOrderProcessing extends AbstractOrderProcessingTemplate {


    public SaveOrderProcessing(OrderProcessingContext orderContext) {
        super(orderContext);
    }

    /**
     * 0
     * 初始化订单信息
     */
    @Override
    protected void init() {
        OrdersInfoSaveReqVO ordersInfoSaveReqVO = context.getOrdersInfoSaveReqVO();
        // 转换对象
        OrdersInfoDO ordersInfo = BeanUtils.toBean(ordersInfoSaveReqVO, OrdersInfoDO.class);
        Db.saveOrUpdate(ordersInfo); //生成id
        List<OrdersDetailDO> ordersDetails = ordersInfoSaveReqVO.getOrdersDetails();
        context.setOrderInfo(ordersInfo);
        context.setOrdersDetails(ordersDetails);

        log.info("[SaveOrderProcessing] 初始化成功...");
    }

    /**
     * 1
     * 校验订单
     *
     * @throws Exception
     */
    @Override
    protected void validateOrder() throws Exception {
        // 校验订单
        ValidationHandler build = new HandleChainBuilder()
                .addHandler(new BrandValidationHandler()) // 品牌校验
                .addHandler(new InventoryValidationHandler()) // 库存校验
                .build();
        build.handle(context);
        log.info("[SaveOrderProcessing] 订单校验通过...");
    }

    /**
     * 2
     * 处理地址
     * 上下文
     *
     * @throws Exception
     */
    @Override
    protected void handleAddress() throws Exception {
        if (isNewAddress()) {
            insertAddress();
        }
    }


    /**
     * 3
     * 计算订单商品合计
     * 上下文
     *
     * @throws Exception
     */
    @Override
    protected void calculateTotal() throws Exception {
        StringBuilder count = new StringBuilder();
        Map<String, Integer> goodsQuantityMap = orderContext.getGoodsQuantityMap();
        goodsQuantityMap.forEach((k, v) -> {
            count.append(k).append("  *  ").append(v).append("\n");
        });
        OrdersInfoDO orderInfo = context.getOrderInfo();
        orderInfo.setTotalGoods(count.toString());
        log.info("[SaveOrderProcessing] 订单商品合计计算成功...");
    }


    /**
     * 4
     * 处理订货额外信息
     * 上下文
     *
     * @throws Exception
     */
    @Override
    protected void generateOrderDetails() throws Exception {
        OrdersInfoDO orderInfo = context.getOrderInfo();
        Opt<Long> longOpt = Opt.of(orderInfo.getId());
        if (longOpt.get() == null) {
            orderInfo.setUpdater(SecurityFrameworkUtils.getLoginUserNickname());
            orderInfo.setUpdateTime(LocalDateTime.now());
        } else {
            // 获取当前日期，转换格式为yyyyMMddHHmm
            LocalDateTime now = LocalDateTime.now();
            String dateStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            orderInfo.setOrderId(String.format("%s%s", OrderNumPrefixType.SALE.getType(), dateStr));
            orderInfo.setOrderDate(LocalDate.now());
            orderInfo.setOrderStatus(OrderStatusType.UN_SUBMITTED.getType()); // 默认未提交
            orderInfo.setCreator(SecurityFrameworkUtils.getLoginUserNickname());
            orderInfo.setCreatorId(Objects.requireNonNull(SecurityFrameworkUtils.getLoginUserId()).intValue());
        }
        log.info("[SaveOrderProcessing] 订单参数新增成功...");
    }

    /**
     * 5
     * 保存订单数据 和 明细数据
     * 上下文
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Long saveOrderData() throws Exception {
        // 保存订单数据 或者更新订单信息数据
        OrdersInfoDO orderInfo = context.getOrderInfo();
        Db.saveOrUpdate(orderInfo);
        log.info("[SaveOrderProcessing] 订单数据保存成功...");

        // 保存订单详情
        saveOrderDetails(context, orderInfo.getId());
        return orderInfo.getId();
    }

    /**
     * 2.1
     * 判断是否为新增地址
     *
     * @return
     */
    private boolean isNewAddress() {
        // 判断是否为新增地址
        OrdersInfoSaveReqVO ordersInfoSaveReqVO = context.getOrdersInfoSaveReqVO();

        if (ordersInfoSaveReqVO.getAddressId() != null) {
            log.info("[SaveOrderProcessing] 地址已存在，无需新增...");
            return false;
        }
        log.info("[SaveOrderProcessing] 新增地址...");
        return true;
    }

    /**
     * 2.2
     * 新增地址
     */
    private void insertAddress() {
        OrdersInfoDO orderInfo = context.getOrderInfo();
        CustomerAddressDO addressDO = CustomerAddressConvert.INSTANCE.convert(orderInfo);
        if (addressDO != null) {
            Db.save(addressDO);
            log.info("[SaveOrderProcessing] 新增地址成功...");
        } else {
            log.info("[SaveOrderProcessing] 新增地址失败...");
        }
    }


}
