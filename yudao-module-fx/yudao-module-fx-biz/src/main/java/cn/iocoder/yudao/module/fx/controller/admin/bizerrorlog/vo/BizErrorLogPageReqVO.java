package cn.iocoder.yudao.module.fx.controller.admin.bizerrorlog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 业务错误日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BizErrorLogPageReqVO extends PageParam {

    @Schema(description = "模块名")
    private String module;

    @Schema(description = "错误类型（类名）", example = "1")
    private String type;

    @Schema(description = "业务ID", example = "1215")
    private String bizId;

    @Schema(description = "错误数据（JSON格式）")
    private String errorData;

    @Schema(description = "错误码")
    private Integer errorCode;

    @Schema(description = "错误信息")
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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}