package cn.iocoder.yudao.module.fx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zrl
 * @date 2024/10/28
 */
@Getter
@AllArgsConstructor
public enum OrderGoodsSaleType {

    /**
     * 销售
     */
    XIAO_SHOU(0, "销售"),

    /**
     * 货补
     */
    HUO_BU(1, "货补"),
    ;


    private final Integer type;
    private final String value;
}
