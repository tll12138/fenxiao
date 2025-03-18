package cn.iocoder.yudao.module.fx.controller.admin.fromaccount.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 -  分销打款账户新增/修改 Request VO")
@Data
public class FromAccountSaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12418")
    private Integer id;

    @Schema(description = "打款账户号", example = "29483")
    private String account;

    @Schema(description = "所属分销商", example = "25852")
    private String customerId;

    @Schema(description = "账户类型", example = "1")
    private Integer accountType;

    @Schema(description = "说明", example = "随便")
    private String remark;

    @Schema(description = "是否有效")
    private String isActive;

    @Schema(description = "累计打款次数")
    private Integer totalNum;

    @Schema(description = "累计打款金额")
    private BigDecimal totalAmt;

    @Schema(description = "打款方名称", example = "赵六")
    private String accountName;

    @Schema(description = "所属账户名称", example = "6609")
    private String accountId;

}