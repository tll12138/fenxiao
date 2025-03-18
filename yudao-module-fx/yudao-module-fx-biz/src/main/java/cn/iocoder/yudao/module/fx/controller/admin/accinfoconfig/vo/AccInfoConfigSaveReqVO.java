package cn.iocoder.yudao.module.fx.controller.admin.accinfoconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 客商账户初始化配置新增/修改 Request VO")
@Data
public class AccInfoConfigSaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1535")
    private Integer id;

    @Schema(description = "业务主体")
    private Integer ywzt;

    @Schema(description = "说明")
    private String about;

    @Schema(description = "关联品牌", example = "9549")
    private Integer brandid;

}