package cn.iocoder.yudao.module.fx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zrl
 * @date 2024/10/28
 */

@Getter
@AllArgsConstructor
public enum SaleType {

    /**
     * 正常销售
     */
    NORMAL(0, "正常销售"),

    /**
     * 换货
     */
    EXCHANGE(1, "换货"),
    /**
     * 临期品销售
     */
    NEAR_EXPIRY(2, "临期品销售"),

    /**
     * 仅退款
     */
    ONLY_REFUND(0, "仅退款")
    ;




    private final Integer type;

    private final String value;

}
