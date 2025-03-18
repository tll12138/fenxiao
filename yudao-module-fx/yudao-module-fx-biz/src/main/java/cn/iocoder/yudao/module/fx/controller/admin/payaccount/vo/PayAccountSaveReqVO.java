package cn.iocoder.yudao.module.fx.controller.admin.payaccount.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 分销支付账户新增/修改 Request VO")
@Data
public class PayAccountSaveReqVO {

    @Schema(description = "序号", example = "17256")
    private Integer id;

    @Schema(description = "类型", example = "2")
    private Integer payType;

    @Schema(description = "分销商账户", example = "2803")
    private String customerId;

    @Schema(description = "付款账户")
    private String accountNo;

    @Schema(description = "说明", example = "随便")
    private String description;

    @Schema(description = "是否可用")
    private String isActive;

}