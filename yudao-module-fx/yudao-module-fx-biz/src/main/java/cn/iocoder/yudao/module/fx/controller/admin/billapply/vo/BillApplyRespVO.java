package cn.iocoder.yudao.module.fx.controller.admin.billapply.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 发票申请 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BillApplyRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2782")
    @ExcelProperty("序号")
    private Integer id;

    @Schema(description = "申请人")
    @ExcelProperty("申请人")
    private BigDecimal applyMan;

    @Schema(description = "申请日期")
    @ExcelProperty("申请日期")
    private String applyDate;

    @Schema(description = "开票日期")
    @ExcelProperty("开票日期")
    private String billDate;

    @Schema(description = "地址及电话")
    @ExcelProperty("地址及电话")
    private String address;

    @Schema(description = "开户行及账号")
    @ExcelProperty("开户行及账号")
    private String bankNo;

    @Schema(description = "金额合计")
    @ExcelProperty("金额合计")
    private BigDecimal amount;

    @Schema(description = "操作员")
    @ExcelProperty("操作员")
    private BigDecimal maker;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "发票类型", example = "2")
    @ExcelProperty(value = "发票类型", converter = DictConvert.class)
    @DictFormat("fx_bill_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private BigDecimal billType;

    @Schema(description = "金额合计(大写)")
    @ExcelProperty("金额合计(大写)")
    private String totalAmount;

    @Schema(description = "销售单")
    @ExcelProperty("销售单")
    private String saleOrder;

    @Schema(description = "发票抬头")
    @ExcelProperty(value = "发票抬头", converter = DictConvert.class)
    @DictFormat("fx_business_entity") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String billHead;

    @Schema(description = "发票附件")
    @ExcelProperty("发票附件")
    private String document;

    @Schema(description = "财务说明")
    @ExcelProperty("财务说明")
    private String financialStatement;

    @Schema(description = "购方名称", example = "赵六")
    @ExcelProperty("购方名称")
    private String purchaserName;

    @Schema(description = "纳税人识别号")
    @ExcelProperty("纳税人识别号")
    private String taxNo;

    @Schema(description = "客户名称", example = "王五")
    @ExcelProperty("客户名称")
    private String cusName;

    @Schema(description = "开票信息")
    @ExcelProperty("开票信息")
    private String billInfo;

    @Schema(description = "开票流程", example = "10673")
    @ExcelProperty("开票流程")
    private String rid;

    @Schema(description = "是否完成", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否完成", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private BigDecimal isOver;

    @Schema(description = "是否芽肌")
    @ExcelProperty(value = "是否芽肌", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private BigDecimal isYj;

    @Schema(description = "发票邮箱")
    @ExcelProperty("发票邮箱")
    private String email;

    @Schema(description = "关联邮箱", example = "17061")
    @ExcelProperty("关联邮箱")
    private String emailId;

    @Schema(description = "发票发送状态")
    @ExcelProperty("发票发送状态")
    private BigDecimal isSend;

    @Schema(description = "业务员id")
    @ExcelProperty("业务员id")
    private Integer salespersonId;

}