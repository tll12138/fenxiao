package cn.iocoder.yudao.module.fx.controller.admin.inventorydata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 分销商品库存新增/修改 Request VO")
@Data
public class InventoryDataSaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17216")
    private Integer id;

    @Schema(description = "商品编码", example = "17898")
    private String skuId;

    @Schema(description = "条形码")
    private String scanCode;

    @Schema(description = "商品资料id", example = "6964")
    private String goodId;

    @Schema(description = "良品库存")
    private BigDecimal qty;

    @Schema(description = "次品库存")
    private BigDecimal defectiveQty;

    @Schema(description = "仓库名称", example = "王五")
    private String warehouseName;

    @Schema(description = "仓库类型", example = "1")
    private BigDecimal type;

    @Schema(description = "所属仓库", example = "29858")
    private String warehouseId;

    @Schema(description = "仓库编码")
    private String warehouseCode;

    @Schema(description = "销售出库数")
    private BigDecimal saleQty;

    @Schema(description = "数据渠道")
    private String channel;

    @Schema(description = "商品品牌")
    private String brand;

    @Schema(description = "规格")
    private String value;

    @Schema(description = "商品名称", example = "赵六")
    private String name;

}