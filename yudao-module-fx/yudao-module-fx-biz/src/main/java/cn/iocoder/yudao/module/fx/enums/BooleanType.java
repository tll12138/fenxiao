package cn.iocoder.yudao.module.fx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zrl
 * @date 2024/10/28
 */
@Getter
@AllArgsConstructor
public enum BooleanType {


    NO(0, "否"),
    YES(1, "是");

    private final Integer type;
    private final String value;
}
