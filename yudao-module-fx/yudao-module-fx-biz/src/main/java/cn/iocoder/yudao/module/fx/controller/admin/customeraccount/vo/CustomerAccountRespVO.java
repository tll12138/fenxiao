package cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo;

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

    @Schema(description = "业务主体")
    @ExcelProperty("业务主体")
    private String company;

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
    private BigDecimal deposit;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}