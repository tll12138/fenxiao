package cn.iocoder.yudao.module.fx.dal.dataobject.jushuitanapi;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author tll
 * @date 2025-04-16 14:17:37
 */
@Data
public class AfterSalesRequest {
    @JSONField(name = "o_id")
    private Long oId;
    @JSONField(name = "as_id")
    private Integer asId;
    @JSONField(name = "so_id")
    private String soId;
    @JSONField(name = "outer_as_id")
    private String outerAsId;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "shop_id")
    private Long shopId;
    @JSONField(name = "unique_id")
    private String uniqueId;
    @JSONField(name = "send_date")
    private String sendDate;
    private List<AfterSalesRequestItem> items;
}
