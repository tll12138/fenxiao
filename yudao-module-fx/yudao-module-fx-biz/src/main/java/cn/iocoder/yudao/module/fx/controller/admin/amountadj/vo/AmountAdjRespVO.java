package cn.iocoder.yudao.module.fx.controller.admin.amountadj.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 分销账户资金调整记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AmountAdjRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1450")
    @ExcelProperty("序号")
    private Integer id;

    @Schema(description = "流程编号", example = "20507")
    @ExcelProperty("流程编号")
    private String soId;

    @Schema(description = "调整金额")
    @ExcelProperty("调整金额")
    private BigDecimal amount;

    @Schema(description = "调整日期")
    @ExcelProperty("调整日期")
    private String orderDate;

    @Schema(description = "调整账户", example = "3960")
    @ExcelProperty("调整账户")
    private String account;

    @Schema(description = "调整说明", example = "随便")
    @ExcelProperty("调整说明")
    private String remark;

    @Schema(description = "调整类型", example = "1")
    @ExcelProperty("调整类型")
    private Integer type;

    @Schema(description = "调整后余额")
    @ExcelProperty("调整后余额")
    private BigDecimal adjustBalance;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}