package cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo;

import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.subcompanyinfo.SubCompanyInfoDO;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.VO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "管理后台 - 销售单新增/修改 Request VO")
@Data
public class OrdersInfoDetailRespVO implements VO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1632")
    private Long id;

    @Schema(description = "发货仓库", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "发货仓库不能为空")
    private String warehouse;


    @Schema(description = "仓库编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "仓库编码不能为空")
    private String warehouseCode;

    @Schema(description = "单据编号，系统生成格式SL+yyyyMMddHHmm", requiredMode = Schema.RequiredMode.REQUIRED, example = "SL202410281559")
    private String orderId;

    @Schema(description = "单据日期", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate orderDate;


    @Schema(description = "是否传erp，默认否 0")
    private Integer isToErp;

    @Schema(description = "地址ID")
    private Long addressId;

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

    @Schema(description = "erp单号, 回传")
    private String erpOrderNumber;

    @Schema(description = "订单状态", example = "2")
    private Integer orderStatus;

    @Schema(description = "发货类型 默认正常发货 0 ", example = "0")
    private Integer sendType;

    @Schema(description = "销售类型", example = "2")
    private Integer salesType;

    @Schema(description = "销售商", example = "30476")
    @Trans(type = TransType.SIMPLE, target = SubCompanyInfoDO.class, fields = "companyName", ref = "supplierName")
    private Long supplierId;

    private String supplierName;

    @Schema(description = "收款经销商", example = "13388")
    @Trans(type = TransType.SIMPLE, target = SubCompanyInfoDO.class, fields = "companyName", ref = "receiveSupplierName")
    private Long receiveSupplierId;

    private String receiveSupplierName;


    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "物流公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "物流公司不能为空")
    private String logisticsCompany;

    @Schema(description = "物流单号， 需要回传")
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

    @Schema(description = "订单类型, 默认是正常销售", example = "2")
    private Integer orderType;

    @Schema(description = "是否无痕发货 默认为空")
    private Integer isTraceless;

    @Schema(description = "提交日期 当前日期")
    private LocalDate commitDate;

    @Schema(description = "是否代发 默认否 0")
    private Integer isDf;

    @Schema(description = "客商代发属性", example = "2")
    private Integer cusDfType;

    @Schema(description = "收货方", example = "15594")
    @Trans(type = TransType.SIMPLE, target = CustomerInfoDO.class, fields = "displayName", ref = "distributorName")
    private Long distributorId;

    @Schema(description = "收货方名称", example = "2")
    private String distributorName;

    @Schema(description = "分销商类型", example = "15594")
    private Integer customerType;

    @Schema(description = "业务归属", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "业务归属不能为空")
    private Integer businessBelong;

    @Schema(description = "分销-销售订单明细列表")
    private List<OrdersDetailDO> ordersDetails;

    @Schema(description = "创建人id")
    private Long creatorId;

}