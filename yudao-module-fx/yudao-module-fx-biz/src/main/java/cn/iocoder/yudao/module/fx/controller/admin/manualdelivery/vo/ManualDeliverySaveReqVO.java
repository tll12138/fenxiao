package cn.iocoder.yudao.module.fx.controller.admin.manualdelivery.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 手动发货信息新增/修改 Request VO")
@Data
public class ManualDeliverySaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "27951")
    private Long id;

    @Schema(description = "快递公司")
    private String expressCompany;

    @Schema(description = "快递公司名称", example = "张三")
    private String expressName;

    @Schema(description = "快递公司编号", example = "6974")
    private String expressId;

    @Schema(description = "快递单号")
    private String express;

    @Schema(description = "销售单号", example = "29215")
    private String soId;

    @Schema(description = "关联销售单", example = "22947")
    private String saleId;

    @Schema(description = "单据状态", example = "1")
    private Integer status;

    @Schema(description = "手动发货原因", example = "不对")
    private String reason;

}