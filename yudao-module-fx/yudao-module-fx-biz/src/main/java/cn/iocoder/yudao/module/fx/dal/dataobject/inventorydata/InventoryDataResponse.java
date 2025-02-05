package cn.iocoder.yudao.module.fx.dal.dataobject.inventorydata;

import lombok.Data;

/**
 * @author tll
 * @date 2025-01-14 14:54:25
 */
@Data
public class InventoryDataResponse {
    private int code;
    private String msg;
    private InventoryData data;
}
