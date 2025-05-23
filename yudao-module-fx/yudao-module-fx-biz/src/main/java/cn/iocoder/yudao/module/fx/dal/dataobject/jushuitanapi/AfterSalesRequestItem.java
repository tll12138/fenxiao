package cn.iocoder.yudao.module.fx.dal.dataobject.jushuitanapi;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author tll
 * @date 2025-04-16 14:19:28
 */
@Data
public class AfterSalesRequestItem {
    @JSONField(name = "sku_id")
    private String skuId;
    private Integer qty;
}
