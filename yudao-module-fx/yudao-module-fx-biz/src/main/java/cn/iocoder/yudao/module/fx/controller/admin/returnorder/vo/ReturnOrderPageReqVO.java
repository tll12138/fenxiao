package cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - FX 销售退货单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReturnOrderPageReqVO extends PageParam {

    @Schema(description = "单据编号", example = "877")
    private String orderId;

    @Schema(description = "单据日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] orderDate;

    @Schema(description = "原销售单")
    private String originOrder;

    @Schema(description = "退货方", example = "28259")
    private Long returnUserId;

    @Schema(description = "物流单号")
    private String logisticsNumber;

    @Schema(description = "是否传erp")
    private Integer isToErp;

    @Schema(description = "传erp时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] toErpTime;

}