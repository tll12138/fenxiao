package cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.subcompanyinfo.SubCompanyInfoDO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.VO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 销售单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrdersInfoRespVO implements VO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1632")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "发货仓库", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("发货仓库")
    private String warehouse;

    @Schema(description = "erp单号")
    @ExcelProperty("erp单号")
    private String erpOrderNumber;

    @Schema(description = "发货数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("发货数量")
    private Integer sendQuantity;

    @Schema(description = "分销商类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "15594")
    @ExcelProperty("分销商类型")
    private Integer customerType;

    @Schema(description = "单据编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32616")
    @ExcelProperty("单据编号")
    private String orderId;

    @Schema(description = "单据日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("单据日期")
    private String orderDate;

    @Schema(description = "订单状态", example = "2")
    @ExcelProperty("订单状态")
    private Integer orderStatus;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "物流公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("物流公司")
    private String logisticsCompany;

    @Schema(description = "物流单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("物流单号")
    private String logisticsNumber;

    @Schema(description = "销售金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("销售金额")
    private BigDecimal salesAmount;

    @Schema(description = "商品合计")
    @ExcelProperty("商品合计")
    private String totalGoods;

    @Schema(description = "外部单号")
    @ExcelProperty("外部单号")
    private String externalOrderNumber;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "收货方", example = "15594")
//    @ExcelProperty("收货方")
    @Trans(type = TransType.SIMPLE, target = CustomerInfoDO.class, fields = "displayName", ref = "distributorName")
    private Long distributorId;

    @Schema(description = "收货方名称")
    private String distributorName;

    @Schema(description = "业务归属", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "业务归属", converter = DictConvert.class)
    @DictFormat("fx_belong") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer businessBelong;

    @Schema(description = "创建者")
    private String creator;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "县")
    private String district;

    @Schema(description = "供应商", example = "15594")
    @Trans(type = TransType.SIMPLE, target = SubCompanyInfoDO.class, fields = "companyName", ref = "supplierName")
    private Long supplierId;
    private String supplierName;

    @Schema(description = "收货方供应商", example = "15594")
    @Trans(type = TransType.SIMPLE, target = SubCompanyInfoDO.class, fields = "companyName", ref = "receiveSupplierName")
    private Long receiveSupplierId;
    private String receiveSupplierName;

    @Schema(description = "渠道")
    private Integer channel;

    @Schema(description = "分销商等级")
    private Integer customerLevel;

    @Schema(description = "收货地址")
    private String address;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "收货人")
    private String manager;

    @Schema(description = "地址id")
    private Long addressId;

    @Schema(description = "创建人id")
    private Long creatorId;

    @Schema(description = "流程实例编号")
    private String processInstanceId;
}