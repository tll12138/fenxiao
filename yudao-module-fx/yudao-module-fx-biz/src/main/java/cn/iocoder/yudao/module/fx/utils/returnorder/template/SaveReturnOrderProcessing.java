package cn.iocoder.yudao.module.fx.utils.returnorder.template;

import cn.hutool.core.lang.Opt;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrderSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorder.ReturnOrderDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorderdetail.ReturnOrderDetailDO;
import cn.iocoder.yudao.module.fx.enums.OrderNumPrefixType;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.utils.returnorder.ReturnOrderProcessingContext;
import cn.iocoder.yudao.module.fx.utils.validate.BrandValidationHandler;
import cn.iocoder.yudao.module.fx.utils.validate.HandleChainBuilder;
import cn.iocoder.yudao.module.fx.utils.validate.QuantityValidationHandler;
import cn.iocoder.yudao.module.fx.utils.validate.ValidationHandler;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class SaveReturnOrderProcessing extends AbstractReturnOrderProcessingTemplate {


    public SaveReturnOrderProcessing(ReturnOrderProcessingContext orderContext) {
        super(orderContext);
    }

    @Override
    protected void AfterInit() {
        List<ReturnOrderDetailDO> ordersDetails = (List<ReturnOrderDetailDO>) orderContext.getOrdersDetails();
        //根据商品id分组，计算数量之和
        Map<String, Integer> quantityMap = ordersDetails.stream().collect(
                Collectors.groupingBy(ReturnOrderDetailDO::getSkuId, Collectors.summingInt(ReturnOrderDetailDO::getCount)));
        List<String> brands = ordersDetails.stream().map(ReturnOrderDetailDO::getBrand).collect(Collectors.toList());
        orderContext.setBrands(brands);
        orderContext.setGoodsQuantityMap(quantityMap);
        log.info("[SaveReturnOrderProcessing ] 商品数量信息...");
    }

    /**
     * 0
     * 初始化订单信息
     */
    @Override
    protected void init() {
        ReturnOrderSaveReqVO returnOrderSaveReqVO = context.getReturnOrderSaveReqVO();
        // 转换对象
        ReturnOrderDO returnOrderDO = BeanUtils.toBean(returnOrderSaveReqVO, ReturnOrderDO.class);
        Db.saveOrUpdate(returnOrderDO); //生成id
        List<ReturnOrderDetailDO> ordersDetails = returnOrderSaveReqVO.getOrdersDetails();
        context.setReturnOrderDO(returnOrderDO);
        context.setOrdersDetails(ordersDetails);
        log.info("[SaveReturnOrderProcessing] 初始化成功...");
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
                .addHandler(new QuantityValidationHandler()) // 退货数量校验
                .build();
        build.handle(context);
        log.info("[SaveReturnOrderProcessing] 订单校验通过...");
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
        ReturnOrderDO returnOrderDO = context.getReturnOrderDO();
        Opt<Long> longOpt = Opt.of(returnOrderDO.getId());
        if (longOpt.get() == null) {
            returnOrderDO.setUpdater(SecurityFrameworkUtils.getLoginUserNickname());
            returnOrderDO.setUpdateTime(LocalDateTime.now());
        } else {
            // 获取当前日期，转换格式为yyyyMMddHHmm
            LocalDateTime now = LocalDateTime.now();
            String dateStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            returnOrderDO.setOrderId(String.format("%s%s", OrderNumPrefixType.RETURN.getType(), dateStr));
            returnOrderDO.setOrderDate(LocalDate.now());
            returnOrderDO.setOrderStatus(OrderStatusType.UN_SUBMITTED.getType()); // 默认未提交
            returnOrderDO.setCreator(SecurityFrameworkUtils.getLoginUserNickname());
        }
        log.info("[SaveReturnOrderProcessing] 订单参数新增成功...");
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
        ReturnOrderDO returnOrderDO = context.getReturnOrderDO();
        Db.saveOrUpdate(returnOrderDO);
        log.info("[SaveReturnOrderProcessing] 订单数据保存成功...");
        // 保存订单商品详情数据
        saveReturnOrderDetails(context, returnOrderDO.getId());
        return returnOrderDO.getId();
    }
}
