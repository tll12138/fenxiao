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
public class SentResponseDo {
    /**
     * 错误描述
     */
    private String msg;
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 是否配送成功
     */
    private Boolean issuccess;
    private SentResponseDataDo data;
}
