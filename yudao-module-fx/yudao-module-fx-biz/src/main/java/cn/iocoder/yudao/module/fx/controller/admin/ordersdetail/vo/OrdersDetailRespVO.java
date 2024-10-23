package cn.iocoder.yudao.module.fx.controller.admin.ordersdetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 分销-销售订单明细 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrdersDetailRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8010")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "主表订单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30752")
    @ExcelProperty("主表订单id")
    private Long orderId;

    @Schema(description = "销售类型", example = "2")
    @ExcelProperty(value = "销售类型", converter = DictConvert.class)
    @DictFormat("fx_detail_return_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer saleType;

    @Schema(description = "商品编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "26052")
    @ExcelProperty("商品编码")
    private String skuId;

    @Schema(description = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("商品名称")
    private String skuName;

    @Schema(description = "商品规格", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("商品规格")
    private String category;

    @Schema(description = "销售价", requiredMode = Schema.RequiredMode.REQUIRED, example = "6904")
    @ExcelProperty("销售价")
    private BigDecimal price;

    @Schema(description = "结算价", example = "26736")
    @ExcelProperty("结算价")
    private BigDecimal salePrice;

    @Schema(description = "数量", example = "28668")
    @ExcelProperty("数量")
    private Integer count;

    @Schema(description = "销售金额")
    @ExcelProperty("销售金额")
    private BigDecimal priceAmount;

    @Schema(description = "商品净重(kg)")
    @ExcelProperty("商品净重(kg)")
    private BigDecimal goodsWeight;

    @Schema(description = "其他仓库可用库存", example = "27131")
    @ExcelProperty("其他仓库可用库存")
    private Integer otherAvailCount;

}