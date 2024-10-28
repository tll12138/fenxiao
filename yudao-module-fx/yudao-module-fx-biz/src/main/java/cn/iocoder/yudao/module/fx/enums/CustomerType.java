package cn.iocoder.yudao.module.fx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zrl
 * @date 2024/10/28
 */

@Getter
@AllArgsConstructor
public enum CustomerType {

    /**
     * 线上客户
     */
    ONLINE(0, "线上客户"),
    /**
     * 线下客户
     */
    OFFLINE(1, "线下客户"),
    ;




    private final Integer type;

    private final String value;

}
