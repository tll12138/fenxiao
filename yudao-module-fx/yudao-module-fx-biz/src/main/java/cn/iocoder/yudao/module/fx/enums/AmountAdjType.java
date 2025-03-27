package cn.iocoder.yudao.module.fx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tll
 * @date 2025/3/26
 */

@Getter
@AllArgsConstructor
public enum AmountAdjType {

    /**
     * 正常销售
     */
    RECHARGE(0, "充值"),

    /**
     * 销售发货扣款
     */
    SALE(1, "发货扣款"),
    /**
     * 退货还款
     */
    RET_SALE(2, "退货还款"),
    /**
     * 暂扣调整
     */
    WITHHOLD_ADJUST(3, "暂扣调整"),
    /**
     * 账户调整
     */
    ACCOUNT_ADJUST(4, "账户调整");


    private final Integer type;

    private final String value;

}
