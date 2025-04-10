package cn.iocoder.yudao.module.fx.controller.admin.jstaftersaledata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 分销退货传聚水潭明细中间新增/修改 Request VO")
@Data
public class JstAfterSaleDataSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9616")
    private Long id;

    @Schema(description = "主表id", example = "1244")
    private Long mainId;

    @Schema(description = "商品编码/商家商品编码", example = "23330")
    private String skuId;

    @Schema(description = "数量/退货数量")
    private Integer qty;

    @Schema(description = "批次单号，需开启配置", example = "1833")
    private String batchId;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "有效期至，系统中需开启相关配置")
    private String expirationDate;

    @Schema(description = "商品单价", example = "22872")
    private BigDecimal salePrice;

    @Schema(description = "生产日期，系统中需开启相关配置")
    private String producedDate;

    @Schema(description = "平台订单明细编号", example = "22079")
    private String outerOiId;

    @Schema(description = "SKU退款金额")
    private BigDecimal amount;

    @Schema(description = "可选: 退货，换货，其它，补发", example = "1")
    private String type;

    @Schema(description = "商品名称", example = "王五")
    private String name;

    @Schema(description = "图片地址")
    private String pic;

    @Schema(description = "属性规格")
    private String propertiesValue;

    @Schema(description = "标记数据来源，值为 2b 或 2c，对应具体主表", example = "1")
    private String sourceType;

}