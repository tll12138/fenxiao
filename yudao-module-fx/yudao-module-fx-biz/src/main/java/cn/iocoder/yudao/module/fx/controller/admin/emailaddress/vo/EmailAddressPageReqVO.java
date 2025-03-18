package cn.iocoder.yudao.module.fx.controller.admin.emailaddress.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 发票邮箱库分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmailAddressPageReqVO extends PageParam {

    @Schema(description = "用户编号", example = "13980")
    private String customerId;

    @Schema(description = "邮箱地址")
    private String email;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

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