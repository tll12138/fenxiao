package cn.iocoder.yudao.module.fx.controller.admin.billinginfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 开票信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BillingInfoRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("id")
    private Integer id;

    @Schema(description = "客商", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("客商")
    private Integer customerId;

    @Schema(description = "购方名称")
    @ExcelProperty("购方名称")
    private String company;

    @Schema(description = "纳税人识别号")
    @ExcelProperty("纳税人识别号")
    private String tax;

    @Schema(description = "开户行及账号")
    @ExcelProperty("开户行及账号")
    private String bank;

    @Schema(description = "地址及电话")
    @ExcelProperty("地址及电话")
    private String address;

    @Schema(description = "是否生效", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否生效", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String isActive;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "发送邮箱")
    @ExcelProperty("发送邮箱")
    private String email;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}