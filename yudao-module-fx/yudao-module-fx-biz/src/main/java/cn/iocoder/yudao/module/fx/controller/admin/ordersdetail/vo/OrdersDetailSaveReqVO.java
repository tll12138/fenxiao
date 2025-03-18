package cn.iocoder.yudao.module.fx.controller.admin.ordersdetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 分销-销售订单明细新增/修改 Request VO")
@Data
public class OrdersDetailSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8010")
    private Long id;

    @Schema(description = "主表订单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30752")
    @NotNull(message = "主表订单id不能为空")
    private Long orderId;

    @Schema(description = "销售类型", example = "2")
    private Integer saleType;

    @Schema(description = "商品编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "26052")
    @NotEmpty(message = "商品编码不能为空")
    private String skuId;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "商品名称不能为空")
    private String skuName;

    @Schema(description = "商品规格", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "商品规格不能为空")
    private String category;

    @Schema(description = "销售价", requiredMode = Schema.RequiredMode.REQUIRED, example = "6904")
    @NotNull(message = "销售价不能为空")
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