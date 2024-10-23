package cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - FX 发货仓库新增/修改 Request VO")
@Data
public class SendRepositorySaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12347")
    private Integer id;

    @Schema(description = "仓库名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "仓库名称不能为空")
    private String name;

    @Schema(description = "仓库类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "仓库类型不能为空")
    private Integer type;

    @Schema(description = "仓库全称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "仓库全称不能为空")
    private String allName;

    @Schema(description = "是否传erp")
    private Integer isToErp;

}