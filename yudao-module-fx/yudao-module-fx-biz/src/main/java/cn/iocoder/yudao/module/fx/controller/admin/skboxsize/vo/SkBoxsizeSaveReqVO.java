package cn.iocoder.yudao.module.fx.controller.admin.skboxsize.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 商品箱规新增/修改 Request VO")
@Data
public class SkBoxsizeSaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5361")
    private Long id;

    @Schema(description = "商品编码", example = "14455")
    private String skuId;

    @Schema(description = "商品名称", example = "李四")
    private String skuName;

    @Schema(description = "箱规")
    private BigDecimal boxSize;

}