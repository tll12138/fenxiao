package cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 分销商账号新增/修改 Request VO")
@Data
public class CustomerAccountSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2534")
    private Long id;

    @Schema(description = "分销商编号", example = "4297")
    private Long distributorId;

    @Schema(description = "业务主体")
    private Integer company;

    @Schema(description = "余额")
    private BigDecimal balance;

    @Schema(description = "账户编号", example = "11358")
    private String accountId;

    @Schema(description = "暂扣金额")
    private BigDecimal detainAmount;

    @Schema(description = "是否冻结")
    private Integer isActive;

    @Schema(description = "押金")
    private BigDecimal deposit;

}