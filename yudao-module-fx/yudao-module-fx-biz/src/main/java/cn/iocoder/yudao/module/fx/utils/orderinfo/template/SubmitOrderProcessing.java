package cn.iocoder.yudao.module.fx.utils.orderinfo.template;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.utils.orderinfo.OrderProcessingContext;
import cn.iocoder.yudao.module.fx.utils.validate.*;
import lombok.extern.slf4j.Slf4j;

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
        orderInfo.setOrderStatus(OrderStatusType.UN_SUBMITTED.getType()); // 默认未提交
        orderInfo.setUpdater(SecurityFrameworkUtils.getLoginUserNickname());
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
        }catch (ServiceException e){
            throw exception(new ErrorCode(e.getCode(),e.getMessage()));
        }
        log.info("[SubmitOrderProcessing] 订单校验通过...");
    }
}
