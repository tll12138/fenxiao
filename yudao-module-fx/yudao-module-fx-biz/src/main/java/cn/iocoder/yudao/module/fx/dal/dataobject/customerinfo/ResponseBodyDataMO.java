package cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class ResponseBodyDataMO {
    private Integer total;
    @JSONField(name = "channel_vos")
    private List<ChannelVo> channelVos;
}
