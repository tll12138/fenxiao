package cn.iocoder.yudao.module.fx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tll
 * @date 2025-04-02 17:27:34
 */
@Getter
@AllArgsConstructor
public enum SaleFromEnum {

    /**
     * 销售单
     */
    SALE("SALE"),
    /**
     * 营销寄样
     */
    MARKET("MARKET"),
    /**
     * 内部仓正品调整
     */
    ZP_ADJUST("ZP_ADJUST"),
    /**
     * 内购
     */
    NG("NG"),
    /**
     * 内部仓次品调整
     */
    CP_ADJUST("CP_ADJUST"),
    ;


    private final String value;

}
