package cn.iocoder.yudao.module.fx.controller.admin.payaccount.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 分销支付账户 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PayAccountRespVO {

    @Schema(description = "序号", example = "17256")
    @ExcelProperty("序号")
    private Integer id;

    @Schema(description = "类型", example = "2")
    @ExcelProperty(value = "类型", converter = DictConvert.class)
    @DictFormat("account_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer payType;

    @Schema(description = "分销商账户", example = "2803")
    @ExcelProperty("分销商账户")
    private String customerId;

    @Schema(description = "分销商账户名称", example = "2803")
    @ExcelProperty("分销商账户名称")
    private String customerName;

    @Schema(description = "付款账户")
    @ExcelProperty("付款账户")
    private String accountNo;

    @Schema(description = "说明", example = "随便")
    @ExcelProperty("说明")
    private String description;

    @Schema(description = "是否可用")
    @ExcelProperty(value = "是否可用", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String isActive;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}