package cn.iocoder.yudao.module.fx.controller.admin.monsettlement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 分销账户月结 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MonSettlementRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "27276")
    @ExcelProperty("序号")
    private Integer id;

    @Schema(description = "年月")
    @ExcelProperty("年月")
    private String month;

    @Schema(description = "月结总余额")
    @ExcelProperty("月结总余额")
    private BigDecimal totalAmount;

    @Schema(description = "暂扣金额")
    @ExcelProperty("暂扣金额")
    private BigDecimal withheldAmount;

    @Schema(description = "可用余额")
    @ExcelProperty("可用余额")
    private BigDecimal availableAmount;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "账户", example = "29726")
    @ExcelProperty("账户")
    private String account;

    @Schema(description = "业务主体")
    @ExcelProperty("业务主体")
    private String company;

}