package cn.iocoder.yudao.module.system.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zrl
 * @date 2024/11/1
 */
@Getter
@AllArgsConstructor
public enum DingTalkPriorityEnum {

    /**
     * 10：较低
     *
     * 20：普通
     *
     * 30：紧急
     *
     * 40：非常紧急
     */

    LOW(10),
    NORMAL(20),
    URGENT(30),
    VERY_URGENT(40);

    /**
     * 性别
     */
    private final Integer type;
}
