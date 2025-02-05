package cn.iocoder.yudao.module.fx.controller.admin.inventorydata.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 分销商品库存 Response VO")
@Data
@ExcelIgnoreUnannotated
public class InventoryDataRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17216")
    @ExcelProperty("序号")
    private Integer id;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "商品编码", example = "17898")
    @ExcelProperty("商品编码")
    private String skuId;

    @Schema(description = "条形码")
    @ExcelProperty("条形码")
    private String scanCode;

    @Schema(description = "商品资料id", example = "6964")
    @ExcelProperty("商品资料id")
    private String goodId;

    @Schema(description = "良品库存")
    @ExcelProperty("良品库存")
    private BigDecimal qty;

    @Schema(description = "次品库存")
    @ExcelProperty("次品库存")
    private BigDecimal defectiveQty;

    @Schema(description = "仓库名称", example = "王五")
    @ExcelProperty("仓库名称")
    private String warehouseName;

    @Schema(description = "仓库类型", example = "1")
    @ExcelProperty("仓库类型")
    private BigDecimal type;

    @Schema(description = "所属仓库", example = "29858")
    @ExcelProperty("所属仓库")
    private String warehouseId;

    @Schema(description = "仓库编码")
    @ExcelProperty("仓库编码")
    private String warehouseCode;

    @Schema(description = "销售出库数")
    @ExcelProperty("销售出库数")
    private BigDecimal saleQty;

    @Schema(description = "数据渠道")
    @ExcelProperty("数据渠道")
    private String channel;

    @Schema(description = "商品品牌")
    @ExcelProperty("商品品牌")
    private String brand;

    @Schema(description = "规格")
    @ExcelProperty("规格")
    private String value;

    @Schema(description = "商品名称", example = "赵六")
    @ExcelProperty("商品名称")
    private String name;

}