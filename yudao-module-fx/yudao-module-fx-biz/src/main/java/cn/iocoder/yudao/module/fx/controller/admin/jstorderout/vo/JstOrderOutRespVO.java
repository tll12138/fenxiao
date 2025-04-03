package cn.iocoder.yudao.module.fx.controller.admin.jstorderout.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 聚水潭发货回传中间表 Response VO")
@Data
@ExcelIgnoreUnannotated
public class JstOrderOutRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20853")
    @ExcelProperty("序号")
    private Long id;

    @Schema(description = "店铺编号", example = "10712")
    @ExcelProperty("店铺编号")
    private Long shopId;

    @Schema(description = "内部单号", example = "15522")
    @ExcelProperty("内部单号")
    private Integer oId;

    @Schema(description = "销售单号", example = "12699")
    @ExcelProperty("销售单号")
    private String soId;

    @Schema(description = "快递公司", example = "王五")
    @ExcelProperty("快递公司")
    private String expressName;

    @Schema(description = "快递单号")
    @ExcelProperty("快递单号")
    private String express;

    @Schema(description = "快递编码")
    @ExcelProperty("快递编码")
    private String expressCode;

    @Schema(description = "转换标记")
    @ExcelProperty(value = "转换标记", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String isTran;

    @Schema(description = "修改时间")
    @ExcelProperty("修改时间")
    private LocalDateTime modified;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}