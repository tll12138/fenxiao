package cn.iocoder.yudao.module.fx.controller.admin.ec2jstorderitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 分销订单上传详情中间 Response VO")
@Data
@ExcelIgnoreUnannotated
public class Ec2jstOrderitemRespVO {

    @Schema(description = "商品编码", example = "7279")
    @ExcelProperty("商品编码")
    private String skuiId;

    @Schema(description = "店铺商品编码", example = "9307")
    @ExcelProperty("店铺商品编码")
    private String shopSkuId;

    @Schema(description = "erp款号", example = "18296")
    @ExcelProperty("erp款号")
    private String iId;

    @Schema(description = "图片地址")
    @ExcelProperty("图片地址")
    private String pic;

    @Schema(description = "商品属性")
    @ExcelProperty("商品属性")
    private String propertiesValue;

    @Schema(description = "成交总额")
    @ExcelProperty("成交总额")
    private BigDecimal amount;

    @Schema(description = "原价", example = "9286")
    @ExcelProperty("原价")
    private BigDecimal basePrice;

    @Schema(description = "数量")
    @ExcelProperty("数量")
    private BigDecimal qty;

    @Schema(description = "商品名称", example = "张三")
    @ExcelProperty("商品名称")
    private String name;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private String refundStatus;

    @Schema(description = "出库oid", example = "13620")
    @ExcelProperty("出库oid")
    private String outerOiId;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "订单表id", example = "7293")
    @ExcelProperty("订单表id")
    private BigDecimal mainid;

}