package cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 分销价格对照 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PricelistRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14700")
    @ExcelProperty("序号")
    private Integer id;

    @Schema(description = "客户")
    @ExcelProperty("客户")
    private String customer;

    @Schema(description = "产品编码", example = "14456")
    @ExcelProperty("产品编码")
    private String skuId;

    @Schema(description = "规格")
    @ExcelProperty("规格")
    private String category;

    @Schema(description = "销售最低价", example = "12214")
    @ExcelProperty("销售最低价")
    private BigDecimal saleprice;

    @Schema(description = "客户等级")
    @ExcelProperty("客户等级")
    @DictFormat("fx_customer_level")
    private Integer distributorLevel;

    @Schema(description = "产品名称", example = "张三")
    @ExcelProperty("产品名称")
    private String name;

    @Schema(description = "是否基础类型")
    @ExcelProperty("是否基础类型")
    @DictFormat("yes_no")
    private String isNormal;

    @Schema(description = "品牌")
    @ExcelProperty("品牌")
    private String brand;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "客户id")
    private Integer customerId;

    @Schema(description = "品牌id")
    private String brandId;

}