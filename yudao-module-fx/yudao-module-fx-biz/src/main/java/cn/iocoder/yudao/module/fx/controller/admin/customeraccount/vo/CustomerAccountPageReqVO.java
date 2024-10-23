package cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 分销商账号分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerAccountPageReqVO extends PageParam {

    @Schema(description = "分销商编号", example = "4297")
    private Long distributorId;

    @Schema(description = "业务主体")
    private Long company;

    @Schema(description = "余额")
    private BigDecimal balance;

    @Schema(description = "账户编号", example = "11358")
    private String accountId;

    @Schema(description = "暂扣金额")
    private BigDecimal detainAmount;

    @Schema(description = "是否冻结")
    private Integer isActive;

    @Schema(description = "押金")
    private BigDecimal deposit;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}