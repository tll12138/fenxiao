package cn.iocoder.yudao.module.fx.controller.admin.carcptaud.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 客商账户收款审核 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CaRcptAudRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11189")
    @ExcelProperty("序号")
    private Integer id;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String orderNo;

    @Schema(description = "费用类型", example = "不好")
    @ExcelProperty(value = "费用类型", converter = DictConvert.class)
    @DictFormat("fx_fee_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer reason;

    @Schema(description = "支付方式", example = "2")
    @ExcelProperty(value = "支付方式", converter = DictConvert.class)
    @DictFormat("fx_payment_method") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer payType;

    @Schema(description = "支付证明 ")
    @ExcelProperty("支付证明 ")
    private String payWarrant;

    @Schema(description = "实际账户", example = "17051")
    @ExcelProperty("实际账户")
    private String account;

    @Schema(description = "实际账户名称", example = "17051")
    @ExcelProperty("实际账户名称")
    private String accountName;

    @Schema(description = "收款金额")
    @ExcelProperty("收款金额")
    private BigDecimal receive;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "分销商")
    @ExcelProperty("分销商")
    private String customer;

    @Schema(description = "分销商名称", example = "张三")
    @ExcelProperty("分销商名称")
    private String customerName;

    @Schema(description = "业务单据", example = "7738")
    @ExcelProperty("业务单据")
    private String soId;

    @Schema(description = "提交人")
    @ExcelProperty("提交人")
    private String submiter;

    @Schema(description = "提交人姓名", example = "芋艿")
    @ExcelProperty("提交人姓名")
    private String submiterName;

    @Schema(description = "打款账户", example = "16470")
    @ExcelProperty("打款账户")
    private String paymentAccount;

    @Schema(description = "客户等级")
    @ExcelProperty(value = "客户等级", converter = DictConvert.class)
    @DictFormat("fx_customer_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer custLevel;

    @Schema(description = "打款账户名称", example = "芋艿")
    @ExcelProperty("打款账户名称")
    private String paymentAccountName;

    @Schema(description = "是否重复")
    @ExcelProperty(value = "是否重复", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String isRepeat;

    @Schema(description = "业务主体")
    @ExcelProperty(value = "业务主体", converter = DictConvert.class)
    @DictFormat("fx_business_entity") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer businessEntity;

    @Schema(description = "申请日期")
    @ExcelProperty("申请日期")
    private String orderDate;

    @Schema(description = "是否节假日")
    @ExcelProperty(value = "是否节假日", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String isWeek;

    @ExcelProperty(value = "流程实例id")
    @Schema(description = "流程实例id")
    private String processInstanceId;

}