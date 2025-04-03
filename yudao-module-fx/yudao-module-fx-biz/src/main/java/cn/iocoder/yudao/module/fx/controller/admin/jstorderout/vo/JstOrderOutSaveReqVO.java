package cn.iocoder.yudao.module.fx.controller.admin.jstorderout.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 聚水潭发货回传中间表新增/修改 Request VO")
@Data
public class JstOrderOutSaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20853")
    private Long id;

    @Schema(description = "店铺编号", example = "10712")
    private Long shopId;

    @Schema(description = "内部单号", example = "15522")
    private Integer oId;

    @Schema(description = "销售单号", example = "12699")
    private String soId;

    @Schema(description = "快递公司", example = "王五")
    private String expressName;

    @Schema(description = "快递单号")
    private String express;

    @Schema(description = "快递编码")
    private String expressCode;

    @Schema(description = "转换标记")
    private String isTran;

    @Schema(description = "修改时间")
    private LocalDateTime modified;

}