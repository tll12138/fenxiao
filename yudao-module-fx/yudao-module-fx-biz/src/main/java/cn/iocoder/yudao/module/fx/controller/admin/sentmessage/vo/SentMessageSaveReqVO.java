package cn.iocoder.yudao.module.fx.controller.admin.sentmessage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 分销发货要求消息新增/修改 Request VO")
@Data
public class SentMessageSaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19939")
    private Long id;

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "单据编号", example = "2002")
    private String soId;

    @Schema(description = "消息内容")
    private String msg;

    @Schema(description = "是否发送")
    private String isSend;

    @Schema(description = "计划发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "webhook")
    private String webhook;

    @Schema(description = "secret")
    private String secret;

    @Schema(description = "仓库", example = "12456")
    private Integer warehouseId;

}