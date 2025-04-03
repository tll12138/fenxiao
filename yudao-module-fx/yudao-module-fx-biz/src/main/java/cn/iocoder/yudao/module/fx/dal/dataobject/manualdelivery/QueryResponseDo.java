package cn.iocoder.yudao.module.fx.dal.dataobject.manualdelivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tll
 * @date 2025-04-02 14:04:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryResponseDo {
    /**
     * 错误描述
     */
    private String msg;
    /**
     * 错误码
     */
    private Integer code;
    private QueryResponseDataDo data;
}
