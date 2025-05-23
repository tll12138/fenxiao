package cn.iocoder.yudao.module.fx.controller.admin.fromaccount.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 -  分销打款账户分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FromAccountPageReqVO extends PageParam {

    @Schema(description = "打款账户号", example = "29483")
    private String account;

    @Schema(description = "所属分销商", example = "25852")
    private String customerId;

    @Schema(description = "所属分销商名称", example = "25852")
    private String customerName;

    @Schema(description = "账户类型", example = "1")
    private Integer accountType;

    @Schema(description = "说明", example = "随便")
    private String remark;

    @Schema(description = "是否有效")
    private String isActive;

    @Schema(description = "累计打款次数")
    private Integer totalNum;

    @Schema(description = "累计打款金额")
    private BigDecimal totalAmt;

    @Schema(description = "打款方名称", example = "赵六")
    private String accountName;

    @Schema(description = "所属账户名称", example = "6609")
    private String accountId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}