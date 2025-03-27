package cn.iocoder.yudao.module.fx.controller.admin.bizerrorlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 业务错误日志新增/修改 Request VO")
@Data
public class BizErrorLogSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1071")
    private Long id;

    @Schema(description = "模块名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "模块名不能为空")
    private String module;

    @Schema(description = "错误类型（类名）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "错误类型（类名）不能为空")
    private String type;

    @Schema(description = "业务ID", example = "1215")
    private String bizId;

    @Schema(description = "错误数据（JSON格式）")
    private String errorData;

    @Schema(description = "错误码")
    private Integer errorCode;

    @Schema(description = "错误信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "错误信息不能为空")
    private String errorMsg;

    @Schema(description = "用户ID", example = "30692")
    private Long userId;

    @Schema(description = "用户类型", example = "1")
    private Integer userType;

    @Schema(description = "请求追踪ID", example = "30546")
    private String traceId;

    @Schema(description = "请求IP")
    private String requestIp;

    @Schema(description = "请求参数")
    private String requestParams;

}