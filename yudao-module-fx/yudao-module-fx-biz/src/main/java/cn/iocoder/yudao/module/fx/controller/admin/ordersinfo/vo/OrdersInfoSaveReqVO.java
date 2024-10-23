package cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo;

import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 销售单新增/修改 Request VO")
@Data
public class OrdersInfoSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1632")
    private Long id;

    @Schema(description = "发货仓库", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "发货仓库不能为空")
    private String warehouse;

    @Schema(description = "指定发货日期")
    private LocalDate specifySendDate;

    @Schema(description = "erp单号")
    private String erpOrderNumber;

    @Schema(description = "是否传erp")
    private Integer isToErp;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @Schema(description = "发货数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "发货数量不能为空")
    private Integer sendQuantity;

    @Schema(description = "收货人")
    private String manager;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "收货地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "收货地址不能为空")
    private String address;

    @Schema(description = "单据编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32616")
    @NotEmpty(message = "单据编号不能为空")
    private String orderId;

    @Schema(description = "单据日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单据日期不能为空")
    private LocalDate orderDate;

    @Schema(description = "订单状态", example = "2")
    private Integer orderStatus;

    @Schema(description = "发货类型", example = "1")
    private Integer sendType;

    @Schema(description = "销售类型", example = "2")
    private Integer salesType;

    @Schema(description = "销售商", example = "30476")
    private Long supplierId;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "物流公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "物流公司不能为空")
    private String logisticsCompany;

    @Schema(description = "物流单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "物流单号不能为空")
    private String logisticsNumber;

    @Schema(description = "销售金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "销售金额不能为空")
    private BigDecimal salesAmount;

    @Schema(description = "大客户地址")
    private String bigCustomerAddress;

    @Schema(description = "客户等级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "客户等级不能为空")
    private Integer customerLevel;

    @Schema(description = "商品合计")
    private String totalGoods;

    @Schema(description = "总重量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总重量不能为空")
    private Double totalWeight;

    @Schema(description = "发货要求")
    private String requirement;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "外部单号")
    private String externalOrderNumber;

    @Schema(description = "渠道")
    private Integer channel;

    @Schema(description = "订单类型", example = "2")
    private Integer orderType;

    @Schema(description = "是否无痕发货")
    private Integer isTraceless;

    @Schema(description = "提交日期")
    private LocalDate commitDate;

    @Schema(description = "是否代发")
    private Integer isDf;

    @Schema(description = "客商代发属性", example = "2")
    private Integer cusDfType;

    @Schema(description = "收款经销商", example = "13388")
    private Long receiveSupplierId;

    @Schema(description = "收货方", example = "15594")
    private Long distributorId;

    @Schema(description = "分销商类型", example = "15594")
    private Integer customerType;

    @Schema(description = "业务归属", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "业务归属不能为空")
    private Integer businessBelong;

    @Schema(description = "分销-销售订单明细列表")
    private List<OrdersDetailDO> ordersDetails;

}