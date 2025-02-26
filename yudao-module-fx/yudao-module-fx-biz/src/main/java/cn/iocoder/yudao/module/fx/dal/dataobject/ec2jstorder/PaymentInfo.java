package cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author tll
 * @date 2025-02-25 11:37:48
 */
@Data
public class PaymentInfo {
    @JsonProperty("outer_pay_id")
    // 外部支付单号
    private String outerPayId;

    @JsonProperty("pay_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // 支付时间
    private String payDate;

    @JsonProperty("payment")
    // 支付方式
    private String payment;

    @JsonProperty("seller_account")
    // 卖家账号
    private String sellerAccount;

    @JsonProperty("buyer_account")
    // 买家账号
    private String buyerAccount;

    @JsonProperty("amount")
    // 支付金额
    private Double amount;
}