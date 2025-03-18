package cn.iocoder.yudao.module.fx.controller.admin.accountadjust.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 分销账户调整 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AccountAdjustRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "6727")
    @ExcelProperty("序号")
    private Integer id;

    @Schema(description = "单据编号")
    @ExcelProperty("单据编号")
    private String orderNo;

    @Schema(description = "费用类型", example = "不对")
    @ExcelProperty("费用类型")
    private String reason;

    @Schema(description = "调整分销账户", example = "9484")
    @ExcelProperty("调整分销账户")
    private String account;

    @Schema(description = "调整说明", example = "随便")
    @ExcelProperty("调整说明")
    private String remark;

    @Schema(description = "提交人")
    @ExcelProperty("提交人")
    private String submiter;

    @Schema(description = "调整金额")
    @ExcelProperty("调整金额")
    private BigDecimal adjAmount;

    @Schema(description = "调整后账户余额")
    @ExcelProperty("调整后账户余额")
    private BigDecimal afterAmount;

    @Schema(description = "当前账户余额")
    @ExcelProperty("当前账户余额")
    private BigDecimal nowAmount;

    @Schema(description = "业务主体")
    @ExcelProperty("业务主体")
    private String company;

    @Schema(description = "当前暂扣账户金额")
    @ExcelProperty("当前暂扣账户金额")
    private BigDecimal nowTempAmount;

    @Schema(description = "调整后暂扣账户金额")
    @ExcelProperty("调整后暂扣账户金额")
    private BigDecimal afterTempAmount;

    @Schema(description = "调整类型", example = "1")
    @ExcelProperty("调整类型")
    private String type;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}