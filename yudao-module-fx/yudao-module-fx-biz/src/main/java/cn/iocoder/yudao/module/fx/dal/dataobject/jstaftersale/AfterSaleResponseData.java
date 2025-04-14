package cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author tll
 * @date 2025-04-14 13:11:21
 */
@Data
public class AfterSaleResponseData {
    @JSONField(name = "datas")
    private List<DataItem2C> dataItem2C;

    @JSONField(name = "data")
    private DataItem2B dataItem2B;
}
