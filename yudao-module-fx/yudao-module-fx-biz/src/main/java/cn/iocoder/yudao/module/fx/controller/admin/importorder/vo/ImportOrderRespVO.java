package cn.iocoder.yudao.module.fx.controller.admin.importorder.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 客商代发单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ImportOrderRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "22725")
    @ExcelProperty("主键")
    private Integer id;

    @Schema(description = "商品数量")
    @ExcelProperty("商品数量")
    private BigDecimal productQuantity;

    @Schema(description = "省份")
    @ExcelProperty("省份")
    private String province;

    @Schema(description = "城市")
    @ExcelProperty("城市")
    private String city;

    @Schema(description = "订单编号", example = "20530")
    @ExcelProperty("订单编号")
    private String soId;

    @Schema(description = "商品编码", example = "20290")
    @ExcelProperty("商品编码")
    private String skuId;

    @Schema(description = "区县")
    @ExcelProperty("区县")
    private String district;

    @Schema(description = "详细地址")
    @ExcelProperty("详细地址")
    private String address;

    @Schema(description = "收货人")
    @ExcelProperty("收货人")
    private String contact;

    @Schema(description = "收货电话")
    @ExcelProperty("收货电话")
    private String mobile;

    @Schema(description = "是否发货")
    @ExcelProperty(value = "是否发货", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer isShipped;

    @Schema(description = "是否生成销售单")
    @ExcelProperty(value = "是否生成销售单", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer isSalesOrderGenerated;

    @Schema(description = "客商", example = "17389")
    @ExcelProperty("客商")
    private String customerid;

    @Schema(description = "发货仓", example = "5540")
    @ExcelProperty("发货仓")
    private String warehouseid;

    @Schema(description = "销售单号")
    @ExcelProperty("销售单号")
    private String saleno;

    @Schema(description = "快递公司")
    @ExcelProperty("快递公司")
    private String expressCompany;

    @Schema(description = "快递单号")
    @ExcelProperty("快递单号")
    private String trackingNumber;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "业务归属")
    @ExcelProperty("业务归属")
    private Integer businessAffiliation;

    @Schema(description = "单价", example = "13024")
    @ExcelProperty("单价")
    private BigDecimal price;

    @Schema(description = "无痕发货")
    @ExcelProperty("无痕发货")
    private Integer isTraceless;

    @Schema(description = "收款经销商", example = "8443")
    @ExcelProperty("收款经销商")
    private String payingDistributorId;

    @Schema(description = "快递公司ID", example = "4418")
    @ExcelProperty("快递公司ID")
    private String expressCompanyId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}