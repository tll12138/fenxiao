package cn.iocoder.yudao.module.fx.controller.admin.bizerrorlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 业务错误日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BizErrorLogRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1071")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "模块名", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("模块名")
    private String module;

    @Schema(description = "错误类型（类名）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("错误类型（类名）")
    private String type;

    @Schema(description = "业务ID", example = "1215")
    @ExcelProperty("业务ID")
    private String bizId;

    @Schema(description = "错误数据（JSON格式）")
    @ExcelProperty("错误数据（JSON格式）")
    private String errorData;

    @Schema(description = "错误码")
    @ExcelProperty("错误码")
    private Integer errorCode;

    @Schema(description = "错误信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("错误信息")
    private String errorMsg;

    @Schema(description = "用户ID", example = "30692")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "用户类型", example = "1")
    @ExcelProperty("用户类型")
    private Integer userType;

    @Schema(description = "请求追踪ID", example = "30546")
    @ExcelProperty("请求追踪ID")
    private String traceId;

    @Schema(description = "请求IP")
    @ExcelProperty("请求IP")
    private String requestIp;

    @Schema(description = "请求参数")
    @ExcelProperty("请求参数")
    private String requestParams;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}