package cn.iocoder.yudao.module.fx.controller.admin.accountadjust.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 分销账户调整分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AccountAdjustPageReqVO extends PageParam {

    @Schema(description = "单据编号")
    private String orderNo;

    @Schema(description = "费用类型", example = "不对")
    private String reason;

    @Schema(description = "调整分销账户", example = "9484")
    private String account;

    @Schema(description = "调整说明", example = "随便")
    private String remark;

    @Schema(description = "提交人")
    private String submiter;

    @Schema(description = "调整金额")
    private BigDecimal adjAmount;

    @Schema(description = "调整后账户余额")
    private BigDecimal afterAmount;

    @Schema(description = "当前账户余额")
    private BigDecimal nowAmount;

    @Schema(description = "业务主体")
    private String company;

    @Schema(description = "当前暂扣账户金额")
    private BigDecimal nowTempAmount;

    @Schema(description = "调整后暂扣账户金额")
    private BigDecimal afterTempAmount;

    @Schema(description = "调整类型", example = "1")
    private String type;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}