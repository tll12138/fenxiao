package cn.iocoder.yudao.module.fx.controller.admin.customeraddress.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 分销商地址 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CustomerAddressDetailRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14382")
    private Long id;

    @Schema(description = "分销商编号", example = "12931")
    private Long distributorId;

    @Schema(description = "联系人")
    private String manager;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @Schema(description = "发货地址")
    private String address;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}