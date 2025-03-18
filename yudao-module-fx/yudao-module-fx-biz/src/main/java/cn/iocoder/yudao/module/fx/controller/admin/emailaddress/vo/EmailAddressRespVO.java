package cn.iocoder.yudao.module.fx.controller.admin.emailaddress.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 发票邮箱库 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EmailAddressRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29513")
    @ExcelProperty("id")
    private Integer id;

    @Schema(description = "用户编号", example = "13980")
    @ExcelProperty("用户编号")
    private String customerId;

    @Schema(description = "邮箱地址")
    @ExcelProperty("邮箱地址")
    private String email;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "开票抬头")
    @ExcelProperty("开票抬头")
    private String company;

    @Schema(description = "税号")
    @ExcelProperty("税号")
    private String tax;

    @Schema(description = "开户行及账号")
    @ExcelProperty("开户行及账号")
    private String bank;

    @Schema(description = "地址及电话")
    @ExcelProperty("地址及电话")
    private String address;

    @Schema(description = "是否可用")
    @ExcelProperty(value = "是否可用", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String isActive;

}