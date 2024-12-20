package cn.iocoder.yudao.module.fx.utils.template;

import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 基础处理上下文接口
 */
@Data
public abstract class BaseProcessingContext {

    /**
     * 品牌列表
     */
    List<String> brands;


    /**
     * 订单明细
     */
    public List<OrdersDetailDO> ordersDetails; // 订单明细


    /**
     * 商品数量
     */
    public Map<String, Integer> goodsQuantityMap; // 商品数量
} 