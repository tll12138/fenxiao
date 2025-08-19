package cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 分销商基础信息新增/修改 Request VO")
@Data
public class CustomerInfoSyncVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13099")
    private Long id;

    @Schema(description = "供应商编号", example = "641")
    private Long supplierId;

    @Schema(description = "分销商编号", example = "2509")
    private Long distributorNum;

    @Schema(description = "分销商名称", example = "李四")
    private String distributorName;

    @Schema(description = "所属子公司")
    private Long subCompany;

    @Schema(description = "显示名称", example = "赵六")
    private String displayName;

    @Schema(description = "业务归属")
    private String belongTo;

    @Schema(description = "分销商等级")
    private Integer distributorLevel;

    @Schema(description = "客户渠道属性")
    private String customerChannelDistribute;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "客户类型", example = "2")
    private Integer customerType;

    @Schema(description = "最近下单时间")
    private String latestOrderDate;

}