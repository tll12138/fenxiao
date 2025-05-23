package cn.iocoder.yudao.module.fx.controller.admin.skucostprice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 商品成本新增/修改 Request VO")
@Data
public class SkuCostpriceSaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4258")
    private Long id;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "商品编码", example = "12936")
    private String skuId;

    @Schema(description = "名称", example = "张三")
    private String name;

    @Schema(description = "规格")
    private String value;

    @Schema(description = "品类", example = "2")
    private Integer type;

    @Schema(description = "属性", example = "8")
    private Integer paid;

    @Schema(description = "财务结算价", example = "25310")
    private BigDecimal costPrice;

    @Schema(description = "采购成本", example = "29377")
    private BigDecimal costOtherprice;

    @Schema(description = "出库成本")
    private BigDecimal outCost;

}