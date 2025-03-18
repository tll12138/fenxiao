package cn.iocoder.yudao.module.fx.controller.admin.amountadj.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 分销账户资金调整记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AmountAdjPageReqVO extends PageParam {

    @Schema(description = "流程编号", example = "20507")
    private String soId;

    @Schema(description = "调整金额")
    private BigDecimal amount;

    @Schema(description = "调整日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] orderDate;

    @Schema(description = "调整账户", example = "3960")
    private String account;

    @Schema(description = "调整说明", example = "随便")
    private String remark;

    @Schema(description = "调整类型", example = "1")
    private Integer type;

    @Schema(description = "调整后余额")
    private BigDecimal adjustBalance;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}