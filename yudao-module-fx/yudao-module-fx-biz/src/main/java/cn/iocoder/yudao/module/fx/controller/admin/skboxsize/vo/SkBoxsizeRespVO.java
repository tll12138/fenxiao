package cn.iocoder.yudao.module.fx.controller.admin.skboxsize.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 商品箱规 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SkBoxsizeRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5361")
    @ExcelProperty("序号")
    private Long id;

    @Schema(description = "商品编码", example = "14455")
    @ExcelProperty("商品编码")
    private String skuId;

    @Schema(description = "商品名称", example = "李四")
    @ExcelProperty("商品名称")
    private String skuName;

    @Schema(description = "箱规")
    @ExcelProperty("箱规")
    private BigDecimal boxSize;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}