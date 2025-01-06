package cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class ResponseBodyMO {
    private String msg;
    private String code;
    private ResponseBodyDataMO data;
    @JSONField(name = "request_id")
    private String requestId;
}
