package cn.iocoder.yudao.module.fx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zrl
 * @date 2024/10/28
 */

@Getter
@AllArgsConstructor
public enum CustomerChannel {
    /**
     * 分销商	0
     * 私域	1
     * 快团	2
     * 淘宝	3
     * 线下	4
     * 直营	5
     * 京东	6
     * 抖音	7
     * 小红书	8
     * 拼多多	9
     */

    DISTRIBUTOR(0, "分销商"),
    PRIVATE(1, "私域"),
    FAST_GROUP(2, "快团"),
    TAO_BAO(3, "淘宝"),
    OFFLINE(4, "线下"),
    DIRECTLY(5, "直营"),
    JD(6, "京东"),
    DOU_YIN(7, "抖音"),
    XIAO_HONG_SHU(8, "小红书"),
    PIN_DUO_DUO(9, "拼多多");

    private final Integer type;
    private final String value;
}
