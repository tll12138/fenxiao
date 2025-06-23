package cn.iocoder.yudao.module.fx.controller.admin.accountcollection.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 分销账户收款记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AccountCollectionPageReqVO extends PageParam {

    @Schema(description = "单据编号")
    private String orderNo;

    @Schema(description = "费用类型", example = "不香")
    private String reason;

    @Schema(description = "支付方式", example = "1")
    private String payType;

    @Schema(description = "支付证明")
    private String payProof;

    @Schema(description = "实际账户", example = "21695")
    private String account;

    @Schema(description = "实际账户名称")
    private String accountName;

    @Schema(description = "收款金额")
    private BigDecimal receive;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "分销商")
    private String customer;

    @Schema(description = "分销商名称")
    private String customerName;

    @Schema(description = "业务单据", example = "7313")
    private String soId;

    @Schema(description = "提交人")
    private String submiter;

    @Schema(description = "打款账户", example = "7322")
    private String payoutAccountId;

    @Schema(description = "客户等级")
    private String level;

    @Schema(description = "打款账户名称", example = "李四")
    private String payoutAccountName;

    @Schema(description = "是否重复")
    private String isRepeat;

    @Schema(description = "业务主体", example = "20110")
    private String mainId;

    @Schema(description = "申请日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] orderDate;

    @Schema(description = "是否周末")
    private String isWeek;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}