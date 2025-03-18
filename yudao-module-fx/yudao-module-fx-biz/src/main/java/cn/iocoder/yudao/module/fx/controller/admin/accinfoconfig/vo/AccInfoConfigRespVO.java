package cn.iocoder.yudao.module.fx.controller.admin.accinfoconfig.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 客商账户初始化配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AccInfoConfigRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1535")
    @ExcelProperty("序号")
    private Integer id;

    @Schema(description = "业务主体")
    @ExcelProperty("业务主体")
    private Integer ywzt;

    @Schema(description = "说明")
    @ExcelProperty("说明")
    private String about;

    @Schema(description = "关联品牌", example = "9549")
    @ExcelProperty("关联品牌")
    private Integer brandid;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}