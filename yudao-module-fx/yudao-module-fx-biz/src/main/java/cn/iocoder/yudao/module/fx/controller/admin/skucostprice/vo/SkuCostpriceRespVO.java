package cn.iocoder.yudao.module.fx.controller.admin.skucostprice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 商品成本 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SkuCostpriceRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4258")
    @ExcelProperty("序号")
    private Long id;

    @Schema(description = "品牌")
    @ExcelProperty(value = "品牌", converter = DictConvert.class)
    @DictFormat("fx_brand") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String brand;

    @Schema(description = "商品编码", example = "12936")
    @ExcelProperty("商品编码")
    private String skuId;

    @Schema(description = "名称", example = "张三")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "规格")
    @ExcelProperty("规格")
    private String value;

    @Schema(description = "品类", example = "2")
    @ExcelProperty("品类")
    private BigDecimal type;

    @Schema(description = "属性", example = "8")
    @ExcelProperty("属性")
    private BigDecimal paid;

    @Schema(description = "财务结算价", example = "25310")
    @ExcelProperty("财务结算价")
    private BigDecimal costPrice;

    @Schema(description = "采购成本", example = "29377")
    @ExcelProperty("采购成本")
    private BigDecimal costOtherprice;

    @Schema(description = "出库成本")
    @ExcelProperty("出库成本")
    private BigDecimal outCost;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}