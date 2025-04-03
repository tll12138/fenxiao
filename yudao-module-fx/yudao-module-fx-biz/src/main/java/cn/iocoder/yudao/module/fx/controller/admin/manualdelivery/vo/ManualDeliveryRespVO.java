package cn.iocoder.yudao.module.fx.controller.admin.manualdelivery.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 手动发货信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ManualDeliveryRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "27951")
    @ExcelProperty("序号")
    private Long id;

    @Schema(description = "快递公司")
    @ExcelProperty(value = "快递公司", converter = DictConvert.class)
    @DictFormat("fx_wl") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String expressCompany;

    @Schema(description = "快递公司名称", example = "张三")
    @ExcelProperty("快递公司名称")
    private String expressName;

    @Schema(description = "快递公司编号", example = "6974")
    @ExcelProperty("快递公司编号")
    private String expressId;

    @Schema(description = "快递单号")
    @ExcelProperty("快递单号")
    private String express;

    @Schema(description = "销售单号", example = "29215")
    @ExcelProperty("销售单号")
    private String soId;

    @Schema(description = "关联销售单", example = "22947")
    @ExcelProperty("关联销售单")
    private String saleId;

    @Schema(description = "单据状态", example = "1")
    @ExcelProperty("单据状态")
    private Integer status;

    @Schema(description = "手动发货原因", example = "不对")
    @ExcelProperty("手动发货原因")
    private String reason;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}