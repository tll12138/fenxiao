package cn.iocoder.yudao.module.fx.controller.admin.billinginfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 开票信息新增/修改 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingInfoSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @Schema(description = "客商", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "客商不能为空")
    private Integer customerId;

    @Schema(description = "购方名称")
    private String company;

    @Schema(description = "纳税人识别号")
    private String tax;

    @Schema(description = "开户行及账号")
    private String bank;

    @Schema(description = "地址及电话")
    private String address;

    @Schema(description = "是否生效", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "是否生效不能为空")
    private String isActive;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "发送邮箱")
    private String email;

    public BillingInfoSaveReqVO(Integer customerId, String purchaserName, String taxNo, String bankNo, String address, String email, String isActive) {
        this.customerId = customerId;
        this.company = purchaserName;
        this.tax = taxNo;
        this.bank = bankNo;
        this.address = address;
        this.email = email;
        this.isActive = isActive;
    }
}