package cn.iocoder.yudao.module.fx.controller.admin.ordersdetail.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 分销-销售订单明细分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrdersDetailPageReqVO extends PageParam {

    @Schema(description = "主表订单id", example = "30752")
    private Long orderId;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "销售类型", example = "2")
    private Integer saleType;

    @Schema(description = "商品编码", example = "26052")
    private String skuId;

    @Schema(description = "商品名称", example = "芋艿")
    private String skuName;

    @Schema(description = "商品规格")
    private String category;

    @Schema(description = "销售价", example = "6904")
    private BigDecimal price;

    @Schema(description = "结算价", example = "26736")
    private BigDecimal salePrice;

    @Schema(description = "数量", example = "28668")
    private Integer count;

    @Schema(description = "销售金额")
    private BigDecimal priceAmount;

    @Schema(description = "商品净重(kg)")
    private BigDecimal weight;

    @Schema(description = "其他仓库可用库存", example = "27131")
    private Integer inventory;

}