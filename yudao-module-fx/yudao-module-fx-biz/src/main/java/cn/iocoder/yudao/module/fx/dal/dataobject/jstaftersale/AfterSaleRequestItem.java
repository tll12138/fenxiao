package cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author tll
 * @date 2025-04-14 10:53:02
 */
@Data
public class AfterSaleRequestItem {
    private BigDecimal amount;
    @JSONField(name = "properties_value")
    private String propertiesValue;
    private int qty;
    private String name;
    @JSONField(name = "sku_id")
    private String skuId;
    private String pic;
    private String type;
    @JSONField(name = "outer_oi_id")
    private String outerOiId;
    @JSONField(name = "batch_id")
    private String batchId;
    @JSONField(name = "expiration_date")
    private String expirationDate;
    @JSONField(name = "produced_date")
    private String producedDate;
}
