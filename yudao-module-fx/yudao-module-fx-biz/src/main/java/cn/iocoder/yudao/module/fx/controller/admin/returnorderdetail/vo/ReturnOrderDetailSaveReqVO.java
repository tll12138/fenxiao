package cn.iocoder.yudao.module.fx.controller.admin.returnorderdetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 销售退货详情新增/修改 Request VO")
@Data
public class ReturnOrderDetailSaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20416")
    private Long id;

    @Schema(description = "退货单id", example = "630")
    private Long mainId;

    @Schema(description = "商品编码", example = "3861")
    private String skuId;

    @Schema(description = "商品名称", example = "李四")
    private String skuName;

    @Schema(description = "规格")
    private String category;

    @Schema(description = "原销售价", example = "3483")
    private BigDecimal originalPrice;

    @Schema(description = "退货价", example = "24124")
    private BigDecimal returnPrice;

    @Schema(description = "成本价", example = "8009")
    private BigDecimal costPrice;

    @Schema(description = "数量", example = "2538")
    private Integer count;

    @Schema(description = "退货金额")
    private BigDecimal saleAmt;

    @Schema(description = "成本金额")
    private BigDecimal costAmt;

    @Schema(description = "原单数量", example = "19161")
    private Integer originalCount;

    @Schema(description = "退货类型", example = "2")
    private Integer retType;

}