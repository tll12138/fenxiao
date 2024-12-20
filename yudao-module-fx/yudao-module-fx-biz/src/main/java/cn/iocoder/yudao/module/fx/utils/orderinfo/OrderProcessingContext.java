package cn.iocoder.yudao.module.fx.utils.orderinfo;

import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.module.fx.utils.template.BaseProcessingContext;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderProcessingContext extends BaseProcessingContext {


    /**
     * 销售订单保存请求信息
     */
    public OrdersInfoSaveReqVO ordersInfoSaveReqVO; // 订单保存请求信息


    /**
     * 销售订单信息
     */
    public OrdersInfoDO orderInfo;  // 订单信息


}
