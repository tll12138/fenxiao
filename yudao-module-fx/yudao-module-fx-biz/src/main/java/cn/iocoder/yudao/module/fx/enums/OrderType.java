package cn.iocoder.yudao.module.fx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zrl
 * @date 2024/10/28
 */
@Getter
@AllArgsConstructor
public enum OrderType {

    NORMAL(0, "正常销售"),
    YI_JIAN_DAI_FA(1, "一件代发"),
    SI_YU(2, "私域订单"),
    PLATFORM_SUPPLY(3, "平台供货"),
    RETURN_EXCHANGE(4, "退换货"),
    ;

    private final Integer type;
    private final String value;
}
