package cn.iocoder.yudao.module.fx.controller.admin.jstaftersale.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 分销退货传聚水潭中间新增/修改 Request VO")
@Data
public class JstAfterSaleSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20735")
    private Long id;

    @Schema(description = "店铺编号", example = "26042")
    private Long shopId;

    @Schema(description = "退货退款单号，平台唯一", example = "31063")
    private String outerAsId;

    @Schema(description = "平台订单号", example = "23089")
    private String soId;

    @Schema(description = "售后类型，普通退货，其它，拒收退货,仅退款,投诉,补发,换货 或 出入库类型:in是入库（其它退货）out是出库（其它出库）", example = "1")
    private String type;

    @Schema(description = "快递公司")
    private String logisticsCompany;

    @Schema(description = "物流单号", example = "7467")
    private String lId;

    @Schema(description = "WAIT_SELLER_AGREE:买家已经申请退款，等待卖家同意,WAIT_BUYER_RETURN_GOODS:卖家已经同意退款，等待买家退货,WAIT_SELLER_CONFIRM_GOODS:买家已经退货，等待卖家确认收货,SELLER_REFUSE_BUYER:卖家拒绝退款,CLOSED:退款关闭(售后单未确认前填写该状态erp的售后单自动作废),SUCCESS:退款成功；可更新，补发、换货售后单确认时需在系统中手动确认", example = "2")
    private String shopStatus;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "BUYER_NOT_RECEIVED:买家未收到货,BUYER_RECEIVED:买家已收到货,BUYER_RETURNED_GOODS:买家已退货,SELLER_RECEIVED:卖家已收到退货；可更新", example = "2")
    private String goodStatus;

    @Schema(description = "问题类型；可更新", example = "2")
    private String questionType;

    @Schema(description = "原单据总金额")
    private BigDecimal totalAmount;

    @Schema(description = "卖家应退金额")
    private BigDecimal refund;

    @Schema(description = "买家应补偿金额")
    private BigDecimal payment;

    @Schema(description = "订单状态0待推送，1推送成功，2推送失败,3入库成功,4回写成功 或 传erp状态，0未传，1成功，2失败", example = "2")
    private Integer orderStatus;

    @Schema(description = "内部售后单号", example = "24859")
    private Long asId;

    @Schema(description = "内部订单号", example = "12423")
    private Long oId;

    @Schema(description = "是否确认")
    private boolean isConfirm;

    @Schema(description = "收货仓编码 或 分仓编号或者三方仓编码", example = "11779")
    private Long wmsCoId;

    @Schema(description = "仓库类型；主仓 = 1, 销退仓 = 2, 进货仓 = 3, 次品仓 = 4, 门店 = 5, 自定义1仓=6，自定义2仓=7, 自定义3仓=8", example = "1")
    private Integer warehouseType;

    @Schema(description = "收货人城市")
    private String receiverCity;

    @Schema(description = "收货人区县")
    private String receiverDistrict;

    @Schema(description = "外部单号（单据上传成功之后对应页面线上单号）", example = "21141")
    private String externalId;

    @Schema(description = "出库类型", example = "张三")
    private String drpCoName;

    @Schema(description = "默认1 ，主仓=1，销退仓=2，进货仓=3，次品仓=4，自定义1仓=6，自定义2仓=7，自定义3仓=8，自定义4仓=9，自定义5仓=10，自定义6仓=11，自定义7仓=12，自定义8仓=13，自定义9仓=14，自定义10仓=15（对应ERP仓库资料设定页面）")
    private Integer warehouse;

    @Schema(description = "收货人电话")
    private String receiverMobile;

    @Schema(description = "收货人省")
    private String receiverState;

    @Schema(description = "收货人名称", example = "张三")
    private String receiverName;

    @Schema(description = "收货地址")
    private String receiverAddress;

    @Schema(description = "标签")
    private String labels;

    @Schema(description = "传erp时间")
    private String toErpTime;

    @Schema(description = "传erp日志")
    private String toErpMsg;

    @Schema(description = "订单来源")
    private String orderFrom;

    @Schema(description = "是否审核单据；0否1是")
    private Integer excuteConfirming;

    @Schema(description = "物流公司编码", example = "15832")
    private String lcId;

    @Schema(description = "聚水潭系统内部单号（单据上传成功之后对应页面出仓单号）", example = "13164")
    private Long ioId;

    @Schema(description = "标记数据来源，值为 2b 或 2c，对应传聚水潭的接口", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "标记数据来源，值为 2b 或 2c，对应传聚水潭的接口不能为空")
    private String sourceType;

}