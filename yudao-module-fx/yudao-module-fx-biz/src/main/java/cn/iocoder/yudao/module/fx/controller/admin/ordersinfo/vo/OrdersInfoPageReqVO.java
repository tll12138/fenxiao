package cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(description = "管理后台 - 销售单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrdersInfoPageReqVO extends PageParam {

    @Schema(description = "erp单号")
    private String erpOrderNumber;

    @Schema(description = "收货人")
    private String manager;

    @Schema(description = "单据编号", example = "32616")
    private String orderId;

    @Schema(description = "单据日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate[] orderDate;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "物流单号")
    private String logisticsNumber;

    @Schema(description = "外部单号")
    private String externalOrderNumber;

    @Schema(description = "订单类型", example = "2")
    private Integer orderType;

    @Schema(description = "收货方", example = "15594")
    private Long distributorId;

    @Schema(description = "业务归属")
    private Integer businessBelong;

    @Schema(description = "分销商类型", example = "15594")
    private Integer customerType;

    @Schema(description = "销售单状态", example = "1")
    private Integer orderStatus;


}