package cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tll
 * @date 2025-04-14 10:46:57
 */
@Data
public class AfterSaleRequest {
    @JSONField(name = "shop_status")
    private String shopStatus;
    @JSONField(name = "question_type")
    private String questionType;
    @JSONField(name = "outer_as_id")
    private String outerAsId;
    private String remark;
    private String type;
    @JSONField(name = "good_status")
    private String goodStatus;
    @JSONField(name = "shop_id")
    private long shopId;
    @JSONField(name = "total_amount")
    private BigDecimal totalAmount;
    @JSONField(name = "so_id")
    private String soId;
    @JSONField(name = "logistics_company")
    private String logisticsCompany;
    private BigDecimal payment;
    @JSONField(name = "l_id")
    private String lId;
    private List<AfterSaleRequestItem> items;
    private BigDecimal refund;
    @JSONField(name = "is_confirm")
    private boolean isConfirm;
    @JSONField(name = "external_id")
    private String externalId;
    @JSONField(serialize = false)
    private Long id;
    @JSONField(serialize = false)
    private String sourceType;
}
