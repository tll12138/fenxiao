package cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author tll
 * @date 2025-02-26 09:29:36
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderUploadRes {
    @JsonProperty("code")
    /* 状态码 */
    private Integer code;

    @JsonProperty("msg")
    /* 结果描述 */
    private String msg;

    @JsonProperty("data")
    /* 响应数据 */
    private OrderUploadResData data;
}
