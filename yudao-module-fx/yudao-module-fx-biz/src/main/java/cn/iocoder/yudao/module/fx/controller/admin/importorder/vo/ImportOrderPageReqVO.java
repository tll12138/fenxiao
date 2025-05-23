package cn.iocoder.yudao.module.fx.controller.admin.importorder.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 客商代发单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ImportOrderPageReqVO extends PageParam {

    @Schema(description = "商品数量")
    private BigDecimal productQuantity;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "订单编号", example = "20530")
    private String soId;

    @Schema(description = "商品编码", example = "20290")
    private String skuId;

    @Schema(description = "区县")
    private String district;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "收货人")
    private String contact;

    @Schema(description = "收货电话")
    private String mobile;

    @Schema(description = "是否发货")
    private Integer isShipped;

    @Schema(description = "是否生成销售单")
    private Integer isSalesOrderGenerated;

    @Schema(description = "客商", example = "17389")
    private String customerid;

    @Schema(description = "发货仓", example = "5540")
    private String warehouseid;

    @Schema(description = "销售单号")
    private String saleno;

    @Schema(description = "快递公司")
    private String expressCompany;

    @Schema(description = "快递单号")
    private String trackingNumber;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "业务归属")
    private Integer businessAffiliation;

    @Schema(description = "单价", example = "13024")
    private BigDecimal price;

    @Schema(description = "无痕发货")
    private Integer isTraceless;

    @Schema(description = "收款经销商", example = "8443")
    private Integer payingDistributorId;

    @Schema(description = "快递公司ID", example = "4418")
    private String expressCompanyId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    private Integer offset;

}