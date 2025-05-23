package cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author tll
 * @date 2025-02-25 11:36:59
 */
@Data
public class OrderUploadReq {
    //------------------------- 基础信息 -------------------------
    @JSONField(name = "shop_id")
    // 店铺编号 
    private Integer shopId;

    @JSONField(name = "plan_delivery_date")
    // 最晚发货时间（非必填，格式：yyyy-MM-dd HH:mm:ss） 
    private String planDeliveryDate;

    //------------------------- 订单标识 -------------------------
    @JSONField(name = "so_id")
    // 系统订单号 
    private String orderNo;

    //------------------------- 收件人信息 -------------------------

    @JSONField(name = "receiver_state")
    // 收货省份 
    private String receiverState;

    @JSONField(name = "receiver_city")
    // 收货城市 
    private String receiverCity;

    @JSONField(name = "receiver_district")
    // 收货区县 
    private String receiverDistrict;

    @JSONField(name = "receiver_address")
    // 详细地址 
    private String receiverAddress;

    @JSONField(name = "receiver_name")
    // 收件人姓名 
    private String receiverName;

    @JSONField(name = "receiver_phone")
    // 联系电话 
    private String receiverPhone;

    //------------------------- 财务信息 -------------------------
    @JSONField(name = "pay_amount")
    // 应付金额 
    private Double payAmount;

    // 运费
    private Double freight;

    //------------------------- 时间信息 -------------------------
    @JSONField(name = "order_date", format = "yyyy-MM-dd HH:mm:ss")
    // 订单日期
    private String orderDate;

    @JSONField(name = "shop_modified", format = "yyyy-MM-dd HH:mm:ss")
    // 最后修改时间
    private String shopModified;

    //------------------------- 物流信息 -------------------------

    @JSONField(name = "logistics_company")
    // 物流公司名称 
    private String logisticsCompany;

    @JSONField(name = "lc_id")
    // 物流公司编码 
    private String lcId;

    //------------------------- 业务信息 -------------------------
    @JSONField(name = "shop_status")
    // 订单状态 
    private String shopStatus;

    @JSONField(name = "shop_buyer_id")
    // 买家账号 
    private String shopBuyerId;

    @JSONField(name = "remark")
    // 卖家备注 
    private String remark;

    @JSONField(name = "seller_flag")
    // 卖家标签 
    private Integer sellerFlag;

    @JSONField(name = "labels")
    // 多标签 
    private String warehouse;

    @JSONField(name = "items")
    // 商品明细
    private List<OrderItem> items;

    @JSONField(name = "pay")
    //支付明细
    private PaymentInfo pay;
}