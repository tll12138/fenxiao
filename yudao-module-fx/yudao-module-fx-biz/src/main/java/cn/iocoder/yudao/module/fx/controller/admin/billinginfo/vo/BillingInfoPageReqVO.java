package cn.iocoder.yudao.module.fx.controller.admin.billinginfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 开票信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BillingInfoPageReqVO extends PageParam {

    @Schema(description = "客商")
    private Integer customerId;

    @Schema(description = "购方名称")
    private String company;

    @Schema(description = "纳税人识别号")
    private String tax;

    @Schema(description = "开户行及账号")
    private String bank;

    @Schema(description = "地址及电话")
    private String address;

    @Schema(description = "是否生效")
    private String isActive;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "发送邮箱")
    private String email;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}