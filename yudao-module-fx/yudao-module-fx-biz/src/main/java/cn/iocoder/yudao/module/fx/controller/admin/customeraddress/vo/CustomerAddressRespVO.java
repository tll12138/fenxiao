package cn.iocoder.yudao.module.fx.controller.admin.customeraddress.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 分销商地址 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CustomerAddressRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14382")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "分销商编号", example = "12931")
    @ExcelProperty("分销商编号")
    private Long distributorId;

    @Schema(description = "联系人")
    @ExcelProperty("联系人")
    private String manager;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String phone;

    @Schema(description = "省份")
    @ExcelProperty("省份")
    private String province;

    @Schema(description = "市")
    @ExcelProperty("市")
    private String city;

    @Schema(description = "区")
    @ExcelProperty("区")
    private String district;

    @Schema(description = "发货地址")
    @ExcelProperty("发货地址")
    private String address;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}