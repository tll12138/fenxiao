package cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author tll
 * @date 2025-04-14 10:46:57
 */
@Data
public class AfterSaleResponse {
    @JSONField(name = "msg")
    private String msg;

    @JSONField(name = "code")
    private int code;

    @JSONField(name = "data")
    private AfterSaleResponseData data;
}
