package cn.iocoder.yudao.module.fx.controller.admin.payaccount.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 分销支付账户分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayAccountPageReqVO extends PageParam {

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}