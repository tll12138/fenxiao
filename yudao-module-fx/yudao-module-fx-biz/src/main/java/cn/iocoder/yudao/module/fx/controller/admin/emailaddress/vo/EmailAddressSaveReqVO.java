package cn.iocoder.yudao.module.fx.controller.admin.emailaddress.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 发票邮箱库新增/修改 Request VO")
@Data
public class EmailAddressSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29513")
    private Integer id;

    @Schema(description = "用户编号", example = "13980")
    private String customerId;

    @Schema(description = "邮箱地址")
    private String email;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "开票抬头")
    private String company;

    @Schema(description = "税号")
    private String tax;

    @Schema(description = "开户行及账号")
    private String bank;

    @Schema(description = "地址及电话")
    private String address;

    @Schema(description = "是否可用")
    private String isActive;

}