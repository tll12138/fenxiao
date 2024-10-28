package cn.iocoder.yudao.module.fx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zrl
 * @date 2024/10/28
 */

@Getter
@AllArgsConstructor
public enum CustomerLevel {

    /**
     * 普通客户
     */
    NORMAL(0, "普通客户"),

    /**
     * 腰部潜力客户
     */
    YAO_BU(1, "腰部潜力客户"),

    /**
     * 头部客户
     */
    HEAD(2, "头部客户"),

    /**
     * 分销商
     */
    DISTRIBUTOR(3, "分销商"),

    /**
     * 线下客户
     */
    OFFLINE(4, "线下客户"),
    ;




    private final Integer type;

    private final String value;

}
