package cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author tll
 * @date 2025-04-14 13:12:34
 */
@Data
public class DataItem2C {
    @JSONField(name = "msg")
    private String msg;

    @JSONField(name = "as_id")
    private int asId;

    @JSONField(name = "issuccess")
    private boolean isSuccess;

    @JSONField(name = "so_id")
    private String soId;

    @JSONField(name = "outer_as_id")
    private String outerAsId;

    @JSONField(name = "o_id")
    private int oId;

    @JSONField(name = "id")
    private int id;

    @JSONField(name = "order_type")
    private String orderType;

    @JSONField(name = "oaid")
    private String oaid;
}
