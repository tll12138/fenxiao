package cn.iocoder.yudao.module.fx.controller.admin.sentmessage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 分销发货要求消息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SentMessageRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19939")
    @ExcelProperty("序号")
    private Long id;

    @Schema(description = "类型", example = "1")
    @ExcelProperty("类型")
    private String type;

    @Schema(description = "单据编号", example = "2002")
    @ExcelProperty("单据编号")
    private String soId;

    @Schema(description = "消息内容")
    @ExcelProperty("消息内容")
    private String msg;

    @Schema(description = "是否发送")
    @ExcelProperty("是否发送")
    private String isSend;

    @Schema(description = "计划发送时间")
    @ExcelProperty("计划发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "webhook")
    @ExcelProperty("webhook")
    private String webhook;

    @Schema(description = "secret")
    @ExcelProperty("secret")
    private String secret;

    @Schema(description = "仓库", example = "12456")
    @ExcelProperty("仓库")
    private Integer warehouseId;

}