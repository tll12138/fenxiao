package cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tll
 * @date 2025-02-25 11:36:59
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderUploadReq {
    //------------------------- 基础信息 -------------------------
    @JsonProperty("shop_id")
    // 店铺编号 
    private Integer shopId;

    @JsonProperty("plan_delivery_date")
    // 最晚发货时间（非必填，格式：yyyy-MM-dd HH:mm:ss） 
    private String planDeliveryDate;

    //------------------------- 订单标识 -------------------------
    @JsonProperty("so_id")
    // 系统订单号 
    private String orderNo;

    //------------------------- 收件人信息 -------------------------

    @JsonProperty("receiver_state")
    // 收货省份 
    private String receiverState;

    @JsonProperty("receiver_city")
    // 收货城市 
    private String receiverCity;

    @JsonProperty("receiver_district")
    // 收货区县 
    private String receiverDistrict;

    @JsonProperty("receiver_address")
    // 详细地址 
    private String receiverAddress;

    @JsonProperty("receiver_name")
    // 收件人姓名 
    private String receiverName;

    @JsonProperty("receiver_phone")
    // 联系电话 
    private String receiverPhone;

    //------------------------- 财务信息 -------------------------
    @JsonProperty("pay_amount")
    // 应付金额 
    private Double payAmount;

    @JsonProperty("freight")
    // 运费 
    private Double freight;

    //------------------------- 时间信息 -------------------------
    @JsonProperty("order_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // 订单日期 
    private String orderDate;

    @JsonProperty("shop_modified")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // 最后修改时间 
    private String shopModified;

    //------------------------- 物流信息 -------------------------

    @JsonProperty("logistics_company")
    // 物流公司名称 
    private String logisticsCompany;

    @JsonProperty("lc_id")
    // 物流公司编码 
    private String lcId;

    //------------------------- 业务信息 -------------------------
    @JsonProperty("shop_status")
    // 订单状态 
    private String shopStatus;

    @JsonProperty("shop_buyer_id")
    // 买家账号 
    private String shopBuyerId;

    @JsonProperty("remark")
    // 卖家备注 
    private String remark;

    @JsonProperty("seller_flag")
    // 卖家标签 
    private Integer sellerFlag;

    @JsonProperty("labels")
    // 多标签 
    private String warehouse;

    @JsonProperty("items")
    // 商品明细
    private List<OrderItem> items;

    @JsonProperty("pay")
    //支付明细
    private PaymentInfo pay;
}