package cn.iocoder.yudao.module.fx.controller.admin.returnorderdetail.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 销售退货详情 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReturnOrderDetailRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20416")
    @ExcelProperty("序号")
    private Long id;

    @Schema(description = "退货单id", example = "630")
    @ExcelProperty("退货单id")
    private Long mainId;

    @Schema(description = "商品编码", example = "3861")
    @ExcelProperty("商品编码")
    private String skuId;

    @Schema(description = "商品名称", example = "李四")
    @ExcelProperty("商品名称")
    private String skuName;

    @Schema(description = "规格")
    @ExcelProperty("规格")
    private String category;

    @Schema(description = "原销售价", example = "3483")
    @ExcelProperty("原销售价")
    private BigDecimal originalPrice;

    @Schema(description = "退货价", example = "24124")
    @ExcelProperty("退货价")
    private BigDecimal returnPrice;

    @Schema(description = "成本价", example = "8009")
    @ExcelProperty("成本价")
    private BigDecimal costPrice;

    @Schema(description = "数量", example = "2538")
    @ExcelProperty("数量")
    private Integer count;

    @Schema(description = "退货金额")
    @ExcelProperty("退货金额")
    private BigDecimal saleAmt;

    @Schema(description = "成本金额")
    @ExcelProperty("成本金额")
    private BigDecimal costAmt;

    @Schema(description = "原单数量", example = "19161")
    @ExcelProperty("原单数量")
    private Integer originalCount;

    @Schema(description = "退货类型", example = "2")
    @ExcelProperty(value = "退货类型", converter = DictConvert.class)
    @DictFormat("fx_business_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer retType;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}