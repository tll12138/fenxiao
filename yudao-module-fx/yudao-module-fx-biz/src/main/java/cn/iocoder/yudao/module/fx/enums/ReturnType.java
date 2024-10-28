package cn.iocoder.yudao.module.fx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

/**
 * @author zrl
 * @date 2024/10/28
 */
@Getter
@AllArgsConstructor
public enum ReturnType {

    /**
     * 物流损耗退货	0
     * 次品退货	1
     * 换货	2
     * 正常退货	3
     * 其他原因退货	4
     * 仅退款	5
     */

    LOGISTICS_LOSS(0, "物流损耗退货"),
    DEFECTIVE(1, "次品退货"),
    EXCHANGE(2, "换货"),
    NORMAL(3, "正常退货"),
    OTHER(4, "其他原因退货"),
    ONLY_REFUND(5, "仅退款"),
    ;

    private final Integer type;
    private final String value;
}
