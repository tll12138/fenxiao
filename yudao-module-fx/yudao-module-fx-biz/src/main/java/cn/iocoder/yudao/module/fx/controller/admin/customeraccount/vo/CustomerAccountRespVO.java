package cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 分销商账号 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CustomerAccountRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2534")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "分销商编号", example = "4297")
    @ExcelProperty("分销商编号")
    private Long distributorId;

    @Schema(description = "余额")
    @ExcelProperty("余额")
    private BigDecimal balance;

    @Schema(description = "账户编号", example = "11358")
    @ExcelProperty("账户编号")
    private String accountId;

    @Schema(description = "暂扣金额")
    @ExcelProperty("暂扣金额")
    private BigDecimal detainAmount;

    @Schema(description = "是否冻结")
    @ExcelProperty("是否冻结")
    private Integer isActive;

    @Schema(description = "押金")
    @ExcelProperty("押金")
    private Integer deposit;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "业务主体", example = "1")
    @ExcelProperty(value = "业务主体", converter = DictConvert.class)
    @DictFormat("fx_business_entity") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer company;

    @Schema(description = "货补虚拟金额")
    @ExcelProperty("货补虚拟金额")
    private BigDecimal vAmount;

    @Schema(description = "是否允许超额提货（0否1是）")
    @ExcelProperty("是否允许超额提货（0否1是）")
    private Integer isAllow;

    @Schema(description = "超额提货额度")
    @ExcelProperty("超额提货额度")
    private BigDecimal quota;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "是否计算货补（0否1是）")
    @ExcelProperty("是否计算货补（0否1是）")
    private Integer isRep;

    @Schema(description = "暂扣货补金额")
    @ExcelProperty("暂扣货补金额")
    private BigDecimal zkVAmount;

    @Schema(description = "账户名", example = "张三")
    @ExcelProperty("账户名")
    private String name;

}