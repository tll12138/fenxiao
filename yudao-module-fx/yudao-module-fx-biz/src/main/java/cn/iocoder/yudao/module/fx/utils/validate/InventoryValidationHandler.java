package cn.iocoder.yudao.module.fx.utils.validate;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.utils.template.BaseProcessingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.ORDERS_GOODS_INVENTORY_NOT_ENOUGH;

/**
 * 库存校验处理器
 */
@Service
@Slf4j
public class InventoryValidationHandler extends ValidationHandler {

    @Override
    public void handle(BaseProcessingContext context) {
        List<OrdersDetailDO> ordersDetails = (List<OrdersDetailDO>) context.getOrdersDetails();
        //根据商品id分组，计算数量之和
        Map<String, Integer> goodsQuantityMap = context.getGoodsQuantityMap();
        Map<String, Integer> availCountMap = ordersDetails.stream().collect(
                Collectors.toMap(
                        OrdersDetailDO::getSkuId,
                        OrdersDetailDO::getInventory,
                        (existing, replacement) -> existing)
        );
        goodsQuantityMap.forEach((k, quantity) -> {
            if (availCountMap.containsKey(k)) {
                // 判断数量是否大于库存
                Integer total = availCountMap.get(k);
                if (quantity > total) {
                    Integer code = ORDERS_GOODS_INVENTORY_NOT_ENOUGH.getCode();
                    String message = String.format(ORDERS_GOODS_INVENTORY_NOT_ENOUGH.getMsg(), k, quantity, total);
                    throw exception(new ErrorCode(code, message)); // 库存不足
                }
            }
        });
        log.info("[InventoryValidationHandler] 库存校验通过...");
        nextHandle(context);
    }
}