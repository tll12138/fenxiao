package cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author tll
 * @date 2025-02-26 09:52:39
 */
@Data
public class ResultData {
    @JsonProperty("o_id")
    /* ERP内部单号 */
    private Integer oId;

    @JsonProperty("so_id")
    /* 系统订单号 */
    private String soId;

    @JsonProperty("issuccess")
    /* 是否成功 */
    private Boolean isSuccess;

    @JsonProperty("msg")
    /* 操作结果描述 */
    private String msg;
}
