package cn.iocoder.yudao.module.fx.controller.admin.fromaccount.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 -  分销打款账户 Response VO")
@Data
@ExcelIgnoreUnannotated
public class FromAccountRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12418")
    @ExcelProperty("序号")
    private Integer id;

    @Schema(description = "打款账户号", example = "29483")
    @ExcelProperty("打款账户号")
    private String account;

    @Schema(description = "所属分销商", example = "25852")
    @ExcelProperty("所属分销商")
    private String customerId;

    @Schema(description = "账户类型", example = "1")
    @ExcelProperty("账户类型")
    private Integer accountType;

    @Schema(description = "说明", example = "随便")
    @ExcelProperty("说明")
    private String remark;

    @Schema(description = "是否有效")
    @ExcelProperty(value = "是否有效", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String isActive;

    @Schema(description = "累计打款次数")
    @ExcelProperty("累计打款次数")
    private Integer totalNum;

    @Schema(description = "累计打款金额")
    @ExcelProperty("累计打款金额")
    private BigDecimal totalAmt;

    @Schema(description = "打款方名称", example = "赵六")
    @ExcelProperty("打款方名称")
    private String accountName;

    @Schema(description = "所属账户名称", example = "6609")
    @ExcelProperty("所属账户名称")
    private String accountId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}