package cn.iocoder.yudao.module.fx.controller.admin.returnorderdetail.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 销售退货详情分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReturnOrderDetailPageReqVO extends PageParam {

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}