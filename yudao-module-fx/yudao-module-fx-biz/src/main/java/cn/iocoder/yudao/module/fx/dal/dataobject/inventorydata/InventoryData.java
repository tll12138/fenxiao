package cn.iocoder.yudao.module.fx.dal.dataobject.inventorydata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author tll
 * @date 2025-01-14 14:55:11
 */
@Data
public class InventoryData {
    @JSONField(name = "page_size")
    private int pageSize;
    @JSONField(name = "page_index")
    private int pageIndex;
    @JSONField(name = "has_next")
    private boolean hasNext;
    private List<InventoryVO> inventorys;
}
