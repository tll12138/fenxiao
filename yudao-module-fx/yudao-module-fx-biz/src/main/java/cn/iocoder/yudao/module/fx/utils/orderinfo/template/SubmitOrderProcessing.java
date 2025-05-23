package cn.iocoder.yudao.module.fx.utils.orderinfo.template;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.module.fx.enums.OrderNumPrefixType;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.utils.orderinfo.OrderProcessingContext;
import cn.iocoder.yudao.module.fx.utils.validate.BalanceValidationHandler;
import cn.iocoder.yudao.module.fx.utils.validate.BrandValidationHandler;
import cn.iocoder.yudao.module.fx.utils.validate.HandleChainBuilder;
import cn.iocoder.yudao.module.fx.utils.validate.InventoryValidationHandler;
import cn.iocoder.yudao.module.fx.utils.validate.ValidationHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @author zrl
 * @date 2024/11/7
 */
@Slf4j
public class SubmitOrderProcessing extends SaveOrderProcessing {

    public SubmitOrderProcessing(OrderProcessingContext orderContext) {
        super(orderContext);
    }

    @Override
    protected void generateOrderDetails() throws Exception {
        OrdersInfoDO orderInfo = context.getOrderInfo();
        // 获取当前日期，转换格式为yyyyMMddHHmm
        LocalDateTime now = LocalDateTime.now();
        String dateStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        orderInfo.setOrderId(String.format("%s%s", OrderNumPrefixType.SALE.getType(), dateStr));
        orderInfo.setOrderDate(LocalDate.now());
        orderInfo.setOrderStatus(OrderStatusType.AUDITING.getType()); // 默认审核中
        orderInfo.setCreator(SecurityFrameworkUtils.getLoginUserNickname());
        orderInfo.setCreatorId(Objects.requireNonNull(SecurityFrameworkUtils.getLoginUserId()).intValue());
        orderInfo.setBrand(context.getBrands().get(0));
        log.info("[SubmitOrderProcessing] 订单参数修改成功...");
    }

    @Override
    protected void validateOrder() throws ServiceException {
        log.info("[SubmitOrderProcessing] 开始校验订单...");

        // 校验订单
        ValidationHandler build = new HandleChainBuilder()
                .addHandler(new BrandValidationHandler()) // 品牌校验
                .addHandler(new InventoryValidationHandler()) // 库存校验
                .addHandler(new BalanceValidationHandler()) // 余额校验
                .build();
        try {
            build.handle(context);
        } catch (ServiceException e) {
            throw exception(new ErrorCode(e.getCode(), e.getMessage()));
        }
        log.info("[SubmitOrderProcessing] 订单校验通过...");
    }
}
