package cn.iocoder.yudao.module.fx.controller.admin.monsettlement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 分销账户月结新增/修改 Request VO")
@Data
public class MonSettlementSaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "27276")
    private Integer id;

    @Schema(description = "年月")
    private String month;

    @Schema(description = "月结总余额")
    private BigDecimal totalAmount;

    @Schema(description = "暂扣金额")
    private BigDecimal withheldAmount;

    @Schema(description = "可用余额")
    private BigDecimal availableAmount;

    @Schema(description = "账户", example = "29726")
    private String account;

    @Schema(description = "业务主体")
    private String company;

}