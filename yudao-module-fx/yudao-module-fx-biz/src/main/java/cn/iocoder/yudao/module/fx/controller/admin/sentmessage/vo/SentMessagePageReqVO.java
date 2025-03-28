package cn.iocoder.yudao.module.fx.controller.admin.sentmessage.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 分销发货要求消息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SentMessagePageReqVO extends PageParam {

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "单据编号", example = "2002")
    private String soId;

    @Schema(description = "消息内容")
    private String msg;

    @Schema(description = "计划发送时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] sendTime;

    @Schema(description = "webhook")
    private String webhook;

    @Schema(description = "secret")
    private String secret;

    @Schema(description = "仓库", example = "12456")
    private Integer warehouseId;

    @Schema(description = "是否发送")
    private Integer isSend;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}