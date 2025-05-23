package cn.iocoder.yudao.module.fx.dal.dataobject.jushuitanapi;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author tll
 * @date 2025-04-16 14:08:26
 */
@Data
public class LogisticsRequestItem {
    @JSONField(name = "oi_id")
    private Long oiId;
    @JSONField(name = "sku_id")
    private String skuId;
    private Integer qty;
    private String name;
    @JSONField(name = "outer_oi_id")
    private String outerOiId;
    @JSONField(name = "so_id")
    private String soId;
}
