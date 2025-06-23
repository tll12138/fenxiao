package cn.iocoder.yudao.module.fx.controller.admin.accountcollection.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 分销账户收款记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AccountCollectionRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27864")
    @ExcelProperty("主键")
    private Integer id;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String orderNo;

    @Schema(description = "费用类型", example = "不香")
    @ExcelProperty("费用类型")
    private String reason;

    @Schema(description = "支付方式", example = "1")
    @ExcelProperty("支付方式")
    private String payType;

    @Schema(description = "支付证明")
    @ExcelProperty("支付证明")
    private String payProof;

    @Schema(description = "实际账户", example = "21695")
    @ExcelProperty("实际账户")
    private String account;

    @Schema(description = "实际账户名称")
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

    @Schema(description = "分销商名称")
    @ExcelProperty("分销商名称")
    private String customerName;

    @Schema(description = "业务单据", example = "7313")
    @ExcelProperty("业务单据")
    private String soId;

    @Schema(description = "提交人")
    @ExcelProperty("提交人")
    private String submiter;

    @Schema(description = "打款账户", example = "7322")
    @ExcelProperty("打款账户")
    private String payoutAccountId;

    @Schema(description = "客户等级")
    @ExcelProperty("客户等级")
    private String level;

    @Schema(description = "打款账户名称", example = "李四")
    @ExcelProperty("打款账户名称")
    private String payoutAccountName;

    @Schema(description = "是否重复")
    @ExcelProperty(value = "是否重复", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String isRepeat;

    @Schema(description = "业务主体", example = "20110")
    @ExcelProperty("业务主体")
    private String mainId;

    @Schema(description = "申请日期")
    @ExcelProperty("申请日期")
    private LocalDateTime orderDate;

    @Schema(description = "是否周末")
    @ExcelProperty(value = "是否周末", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String isWeek;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}