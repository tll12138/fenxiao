package cn.iocoder.yudao.module.fx.controller.admin.carcptaud.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 客商账户收款审核分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CaRcptAudPageReqVO extends PageParam {

    @Schema(description = "单据编号")
    private String orderNo;

    @Schema(description = "费用类型", example = "不好")
    private Integer reason;

    @Schema(description = "支付方式", example = "2")
    private Integer payType;

    @Schema(description = "支付证明 ")
    private String payWarrant;

    @Schema(description = "实际账户", example = "17051")
    private String account;

    @Schema(description = "实际账户名称", example = "17051")
    private String accountName;

    @Schema(description = "收款金额")
    private BigDecimal receive;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "分销商")
    private String customer;

    @Schema(description = "分销商名称", example = "张三")
    private String customerName;

    @Schema(description = "业务单据", example = "7738")
    private String soId;

    @Schema(description = "提交人")
    private String submiter;

    @Schema(description = "提交人姓名", example = "芋艿")
    private String submiterName;

    @Schema(description = "打款账户", example = "16470")
    private String paymentAccount;

    @Schema(description = "客户等级")
    private Integer custLevel;

    @Schema(description = "打款账户名称", example = "芋艿")
    private String paymentAccountName;

    @Schema(description = "是否重复")
    private String isRepeat;

    @Schema(description = "业务主体")
    private Integer businessEntity;

    @Schema(description = "申请日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private String[] orderDate;

    @Schema(description = "是否节假日")
    private String isWeek;

    @Schema(description = "流程实例id")
    private String processInstanceId;

}