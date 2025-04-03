package cn.iocoder.yudao.module.fx.dal.dataobject.manualdelivery;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tll
 * @date 2025-04-02 14:06:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentResponseDataItemDo {
    /**
     * 配送结果描述
     */
    private String msg;
    /**
     * 是否配送成功
     */
    private Boolean issuccess;
    /**
     * 订单ID
     */
    @JSONField(name = "o_id")
    private Long oId;
}
