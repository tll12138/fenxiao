package cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - FX 发货仓库 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SendRepositoryRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12347")
    @ExcelProperty("主键ID")
    private Integer id;

    @Schema(description = "仓库名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("仓库名称")
    private String name;

    @Schema(description = "仓库类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "仓库类型", converter = DictConvert.class)
    @DictFormat("fx_repository_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer type;

    @Schema(description = "仓库全称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("仓库全称")
    private String allName;

    @Schema(description = "是否传erp")
    @ExcelProperty("是否传erp")
    private Integer isToErp;

}