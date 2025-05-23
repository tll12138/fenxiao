package cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author tll
 * @date 2025-02-25 11:37:48
 */
@Data
public class PaymentInfo {
    @JSONField(name = "outer_pay_id")
    // 外部支付单号
    private String outerPayId;

    @JSONField(name = "pay_date", format = "yyyy-MM-dd HH:mm:ss")
    // 支付时间
    private String payDate;

    @JSONField(name = "payment")
    // 支付方式
    private String payment;

    @JSONField(name = "seller_account")
    // 卖家账号
    private String sellerAccount;

    @JSONField(name = "buyer_account")
    // 买家账号
    private String buyerAccount;

    @JSONField(name = "amount")
    // 支付金额
    private Double amount;
}