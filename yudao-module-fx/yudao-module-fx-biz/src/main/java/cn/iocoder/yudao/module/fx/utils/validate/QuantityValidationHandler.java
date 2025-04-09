package cn.iocoder.yudao.module.fx.utils.validate;

import cn.iocoder.yudao.module.fx.dal.dataobject.returnorderdetail.ReturnOrderDetailDO;
import cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.fx.utils.template.BaseProcessingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 退货单退货数量校验
 *
 * @author zrl
 * @date 2024/12/9
 */
@Service
@Slf4j
public class QuantityValidationHandler extends ValidationHandler {

    @Override
    public void handle(BaseProcessingContext context) {
        List<ReturnOrderDetailDO> ordersDetails = (List<ReturnOrderDetailDO>) context.getOrdersDetails();
        ordersDetails.forEach(ordersDetail -> {
            if (ordersDetail.getCount() > ordersDetail.getOriginalCount()) {
                throw exception(ErrorCodeConstants.RETURN_ORDER_DETAIL_QUANTITY_ILLEGAL_1);
            }
            if (ordersDetail.getCount() < 0) {
                throw exception(ErrorCodeConstants.RETURN_ORDER_DETAIL_QUANTITY_ILLEGAL_2);
            }
        });
        log.info("[QuantityValidationHandler] 退货数量校验通过...");
    }
}
