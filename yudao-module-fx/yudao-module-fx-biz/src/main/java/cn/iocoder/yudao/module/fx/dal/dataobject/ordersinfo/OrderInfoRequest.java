package cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo;

import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import lombok.Data;

import java.util.List;

/**
 * @author tll
 * @date 2025-08-18 09:43:01
 */
@Data
public class OrderInfoRequest {
    private List<OrdersInfoDO> ordersList;
    private List<OrdersDetailDO> ordersDetailList;
}
