package cn.iocoder.yudao.module.fx.dal.dataobject.jushuitanapi;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author tll
 * @date 2025-04-16 14:08:02
 */
@Data
public class LogisticsRequest {
    @JSONField(name = "o_id")
    private Long oId;
    @JSONField(name = "l_id")
    private String lId;
    @JSONField(name = "so_id")
    private String soId;
    @JSONField(name = "lc_id")
    private String lcId;
    @JSONField(name = "order_from")
    private String orderFrom;
    @JSONField(name = "wms_co_id")
    private Long wmsCoId;
    @JSONField(name = "logistics_company")
    private String logisticsCompany;
    @JSONField(name = "send_date")
    private String sendDate;
    private List<LogisticsRequestItem> items;
}
