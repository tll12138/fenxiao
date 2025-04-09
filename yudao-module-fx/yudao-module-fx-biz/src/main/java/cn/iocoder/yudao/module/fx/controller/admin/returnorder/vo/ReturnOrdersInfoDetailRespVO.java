package cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorderdetail.ReturnOrderDetailDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author zrl
 * @date 2024/12/9
 */
@Data
public class ReturnOrdersInfoDetailRespVO extends BaseDO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9261")
    private Long id;

    @Schema(description = "退货类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "退货类型不能为空")
    private Integer returnType;

    @Schema(description = "原销售单", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "原销售单不能为空")
    private String originOrder;

    @Schema(description = "退货方", requiredMode = Schema.RequiredMode.REQUIRED, example = "28259")
    @NotNull(message = "退货方不能为空")
    private Long returnUserId;

    @Schema(description = "退货经销商", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "退货经销商不能为空")
    private Long returnDealer;

    @Schema(description = "收货经销商", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "收货经销商不能为空")
    private Long receiveDealer;

    @Schema(description = "收货仓库", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "收货仓库不能为空")
    private String warehouse;

    @Schema(description = "仓库功能", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "仓库功能不能为空")
    private String warehouseFeature;

    @Schema(description = "物流单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "物流单号不能为空")
    private String logisticsNumber;

    @Schema(description = "物流公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "物流公司不能为空")
    private String logisticsCompany;

    @Schema(description = "发货仓库编码")
    private String warehouseCode;

    @Schema(description = "退货业务类型", example = "1")
    @NotNull(message = "退货业务类型不能为空")
    private Integer returnBusinessType;

    @Schema(description = "原单数量")
    private Integer originQuantity;

    @Schema(description = "备注", example = "客户巴拉巴拉")
    private String remark;

    @Schema(description = "总退货金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总退货金额不能为空")
    private BigDecimal totalReturnAmount;

    @Schema(description = "总退货数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总退货数量不能为空")
    private Integer totalReturnQuantity;

    @Schema(description = "退货方名称")
    private String returnUserName;


    // ===========================  待生成信息  ===========================

    @Schema(description = "单据编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "877")
    private String orderId;

    @Schema(description = "单据日期", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate orderDate;

    @Schema(description = "单据状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer orderStatus;

    @Schema(description = "erp单号")
    private String erpOrderNumber;

    @Schema(description = "传erp时间")
    private LocalDate toErpTime;


    // ===========================  默认信息  ===========================
    @Schema(description = "是否传erp")
    private Integer isToErp;


    // ===========================  明细信息  ===========================
    @Schema(description = "退货订单-商品明细列表")
    @NotNull(message = "退货订单-商品明细列表不能为空")
    private List<ReturnOrderDetailDO> ordersDetails;
}
