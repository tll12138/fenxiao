package cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author tll
 * @date 2025-04-14 13:12:34
 */
@Data
public class DataItem2B {
    @JSONField(name = "external_id")
    private String externalId;

    @JSONField(name = "io_id")
    private int ioId;
}
