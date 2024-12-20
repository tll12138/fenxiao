package cn.iocoder.yudao.module.fx.utils.returnorder.template;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorder.ReturnOrderDO;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.utils.returnorder.ReturnOrderProcessingContext;
import cn.iocoder.yudao.module.fx.utils.validate.*;
import lombok.extern.slf4j.Slf4j;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @author zrl
 * @date 2024/11/7
 */
@Slf4j
public class SubmitReturnOrderProcessing extends SaveReturnOrderProcessing {

    public SubmitReturnOrderProcessing(ReturnOrderProcessingContext orderContext) {
        super(orderContext);
    }

    @Override
    protected void generateOrderDetails() throws Exception {
        ReturnOrderDO orderInfo = context.getReturnOrderDO();
        orderInfo.setOrderStatus(OrderStatusType.SUBMITTED.getType()); // 修改为提交
        orderInfo.setUpdater(SecurityFrameworkUtils.getLoginUserNickname());
        log.info("[SubmitReturnOrderProcessing] 订单参数修改成功...");
    }

    @Override
    protected void validateOrder() throws ServiceException {
        log.info("[SubmitReturnOrderProcessing] 开始校验订单...");

        // 校验订单
        ValidationHandler build = new HandleChainBuilder()
                .addHandler(new BrandValidationHandler()) // 品牌校验
                .build();
        try {
            build.handle(context);
        }catch (ServiceException e){
            throw exception(new ErrorCode(e.getCode(),e.getMessage()));
        }
        log.info("[SubmitReturnOrderProcessing] 订单校验通过...");
    }
}
