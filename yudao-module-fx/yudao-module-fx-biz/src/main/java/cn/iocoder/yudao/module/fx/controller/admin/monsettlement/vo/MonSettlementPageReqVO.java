package cn.iocoder.yudao.module.fx.controller.admin.monsettlement.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 分销账户月结分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MonSettlementPageReqVO extends PageParam {

    @Schema(description = "年月")
    private String month;

    @Schema(description = "月结总余额")
    private BigDecimal totalAmount;

    @Schema(description = "暂扣金额")
    private BigDecimal withheldAmount;

    @Schema(description = "可用余额")
    private BigDecimal availableAmount;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "账户", example = "29726")
    private String account;

    @Schema(description = "账户名称", example = "29726")
    private String accountName;

    @Schema(description = "业务主体")
    private String company;

}