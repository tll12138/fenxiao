package cn.iocoder.yudao.module.fx.controller.admin.customeraddress.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 分销商地址新增/修改 Request VO")
@Data
public class CustomerAddressSaveReqVO {

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

}