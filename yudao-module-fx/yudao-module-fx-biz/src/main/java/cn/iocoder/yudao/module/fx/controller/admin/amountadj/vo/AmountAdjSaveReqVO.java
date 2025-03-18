package cn.iocoder.yudao.module.fx.controller.admin.amountadj.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 分销账户资金调整记录新增/修改 Request VO")
@Data
public class AmountAdjSaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1450")
    private Integer id;

    @Schema(description = "流程编号", example = "20507")
    private String soId;

    @Schema(description = "调整金额")
    private BigDecimal amount;

    @Schema(description = "调整日期")
    private String orderDate;

    @Schema(description = "调整账户", example = "3960")
    private String account;

    @Schema(description = "调整说明", example = "随便")
    private String remark;

    @Schema(description = "调整类型", example = "1")
    private Integer type;

    @Schema(description = "调整后余额")
    private BigDecimal adjustBalance;

}