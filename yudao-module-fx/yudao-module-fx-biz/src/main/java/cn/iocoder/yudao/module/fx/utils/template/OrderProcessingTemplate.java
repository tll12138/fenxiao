package cn.iocoder.yudao.module.fx.utils.template;

import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.fx.service.ServiceFactory;
import cn.iocoder.yudao.module.fx.utils.SpringContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Slf4j
public abstract class OrderProcessingTemplate{

    protected BaseProcessingContext orderContext;
    protected final ServiceFactory serviceFactory;

    public OrderProcessingTemplate(BaseProcessingContext orderContext) {
        this.orderContext = orderContext;
        this.serviceFactory = SpringContextHolder.getBean(ServiceFactory.class);
    }

    public final Long processOrder() throws Exception {
        if (orderContext == null){
            throw exception(ErrorCodeConstants.SYSTEM_ERROR);
        }
        init();
        AfterInit(); // 抽取公共方法
        validateOrder();
        handleAddress();
        calculateTotal();
        generateOrderDetails();
        return saveOrderData();
    }

    void AfterInit(){
        List<OrdersDetailDO> ordersDetails = orderContext.getOrdersDetails();
        List<String> brands = ordersDetails.stream().map(OrdersDetailDO::getBrand).collect(Collectors.toList());
        orderContext.setBrands(brands);
        //根据商品id分组，计算数量之和
        Map<String, Integer> quantityMap = ordersDetails.stream().collect(
                Collectors.groupingBy(OrdersDetailDO::getSkuId, Collectors.summingInt(OrdersDetailDO::getCount)));
        orderContext.setGoodsQuantityMap(quantityMap);
        log.info("[OrderProcessingTemplate ] 初始化品牌信息 和 商品数量信息...");
    }

    protected abstract void init();

    /**
     * 校验订单
     * @throws Exception 异常信息
     */
    protected abstract void validateOrder() throws Exception;

    /**
     * 收货地址处理
     * @throws Exception 异常信息
     */
    protected abstract void handleAddress() throws Exception;

    /**
     * 计算合计
     * @throws Exception 异常信息
     */
    protected abstract void calculateTotal() throws Exception;

    /**
     * 生成订单明细
     * @throws Exception 异常信息
     */
    protected abstract void generateOrderDetails() throws Exception;


    /**
     *  保存订单数据
     * @return 订单ID
     * @throws Exception 异常信息
     */
    protected abstract Long saveOrderData() throws Exception;

    public static void saveOrderDetails(BaseProcessingContext context, Long orderId){
        List<OrdersDetailDO> ordersDetails = context.getOrdersDetails();
        List<Long> goodsIdList = ordersDetails.stream()
                .map(OrdersDetailDO::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        LambdaQueryWrapper<OrdersDetailDO> queryWrapper =
                Wrappers.lambdaQuery(OrdersDetailDO.class)
                        .eq(OrdersDetailDO::getOrderId, orderId)
                        .notIn(OrdersDetailDO::getId, goodsIdList);
        long count = Db.count(queryWrapper);
        if (count > 0) {
            log.info("[SaveOrderProcessing] 订单明细数据已存在，删除...");
            boolean remove = Db.remove(queryWrapper);
            if (!remove) {
                log.info("[SaveOrderProcessing] 删除订单明细数据失败...");
            }
        }
        ordersDetails.forEach(ordersDetailDO -> {
            if (ordersDetailDO.getGoodsId() == null) {
                Long id = ordersDetailDO.getId();
                ordersDetailDO.setGoodsId(id);
                ordersDetailDO.setId(null);
            }
            ordersDetailDO.setOrderId(orderId);
        });
        Db.saveOrUpdateBatch(ordersDetails);
        log.info("[SaveOrderProcessing] 保存订单明细数据成功...");
    }
}
