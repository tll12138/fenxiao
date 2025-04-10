package cn.iocoder.yudao.module.fx.controller.admin.jstaftersaledata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 分销退货传聚水潭明细中间 Response VO")
@Data
@ExcelIgnoreUnannotated
public class JstAfterSaleDataRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9616")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "主表id", example = "1244")
    @ExcelProperty("主表id")
    private Long mainId;

    @Schema(description = "商品编码/商家商品编码", example = "23330")
    @ExcelProperty("商品编码/商家商品编码")
    private String skuId;

    @Schema(description = "数量/退货数量")
    @ExcelProperty("数量/退货数量")
    private Integer qty;

    @Schema(description = "批次单号，需开启配置", example = "1833")
    @ExcelProperty("批次单号，需开启配置")
    private String batchId;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "有效期至，系统中需开启相关配置")
    @ExcelProperty("有效期至，系统中需开启相关配置")
    private String expirationDate;

    @Schema(description = "商品单价", example = "22872")
    @ExcelProperty("商品单价")
    private BigDecimal salePrice;

    @Schema(description = "生产日期，系统中需开启相关配置")
    @ExcelProperty("生产日期，系统中需开启相关配置")
    private String producedDate;

    @Schema(description = "平台订单明细编号", example = "22079")
    @ExcelProperty("平台订单明细编号")
    private String outerOiId;

    @Schema(description = "SKU退款金额")
    @ExcelProperty("SKU退款金额")
    private BigDecimal amount;

    @Schema(description = "可选: 退货，换货，其它，补发", example = "1")
    @ExcelProperty("可选: 退货，换货，其它，补发")
    private String type;

    @Schema(description = "商品名称", example = "王五")
    @ExcelProperty("商品名称")
    private String name;

    @Schema(description = "图片地址")
    @ExcelProperty("图片地址")
    private String pic;

    @Schema(description = "属性规格")
    @ExcelProperty("属性规格")
    private String propertiesValue;

    @Schema(description = "标记数据来源，值为 2b 或 2c，对应具体主表", example = "1")
    @ExcelProperty("标记数据来源，值为 2b 或 2c，对应具体主表")
    private String sourceType;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}