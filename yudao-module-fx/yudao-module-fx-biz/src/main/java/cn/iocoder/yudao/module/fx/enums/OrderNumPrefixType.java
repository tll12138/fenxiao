package cn.iocoder.yudao.module.fx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zrl
 * @date 2024/11/6
 */
@Getter
@AllArgsConstructor
public enum OrderNumPrefixType {



    SALE("SL", "销售"),
    HB("HB", "货补"),
    RETURN("SR", "退货"),
    ;

    private final String type;
    private final String value;
}
