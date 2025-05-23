package cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author tll
 * @date 2025-02-25 11:37:30
 */
@Data
public class OrderItem {
    @JSONField(name = "sku_id")
    // 商品编码
    private String skuId;

    @JSONField(name = "shop_sku_id")
    // 店铺商品编码
    private String shopSkuId;

    @JSONField(name = "amount")
    // 商品金额
    private Double amount;

    @JSONField(name = "base_price")
    // 商品单价
    private Double basePrice;

    @JSONField(name = "qty")
    // 购买数量
    private Integer qty;

    @JSONField(name = "name")
    // 商品名称
    private String name;

    @JSONField(name = "outer_oi_id")
    // 唯一值
    private String outerOiId;
}
