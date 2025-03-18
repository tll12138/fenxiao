package cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 分销商账号新增/修改 Request VO")
@Data
public class CustomerAccountSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2534")
    private Long id;

    @Schema(description = "分销商编号", example = "4297")
    private Long distributorId;

    @Schema(description = "余额")
    private BigDecimal balance;

    @Schema(description = "账户编号", example = "11358")
    private String accountId;

    @Schema(description = "暂扣金额")
    private BigDecimal detainAmount;

    @Schema(description = "是否冻结")
    private Integer isActive;

    @Schema(description = "押金")
    private Integer deposit;

    @Schema(description = "业务主体", example = "1")
    private Integer company;

    @Schema(description = "货补虚拟金额")
    private BigDecimal vAmount;

    @Schema(description = "是否允许超额提货（0否1是）")
    private Integer isAllow;

    @Schema(description = "超额提货额度")
    private BigDecimal quota;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "是否计算货补（0否1是）")
    private Integer isRep;

    @Schema(description = "暂扣货补金额")
    private BigDecimal zkVAmount;

    @Schema(description = "账户名", example = "张三")
    private String name;

}