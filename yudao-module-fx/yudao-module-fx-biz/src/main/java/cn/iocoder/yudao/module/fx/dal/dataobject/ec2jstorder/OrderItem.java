package cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author tll
 * @date 2025-02-25 11:37:30
 */
@Data
public class OrderItem {
    @JsonProperty("sku_id")
    // 商品编码
    private String skuId;

    @JsonProperty("shop_sku_id")
    // 店铺商品编码
    private String shopSkuId;

    @JsonProperty("amount")
    // 商品金额
    private Double amount;

    @JsonProperty("base_price")
    // 商品单价
    private Double basePrice;

    @JsonProperty("qty")
    // 购买数量
    private Integer qty;

    @JsonProperty("name")
    // 商品名称
    private String name;
}
