package cn.iocoder.yudao.module.fx.dal.dataobject.manualdelivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author tll
 * @date 2025-04-02 14:05:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryResponseDataDo {
    //数据集合
    private List<QueryResponseDataItemDo> datas;
    private Integer page_index;
    private Boolean has_next;
    private Integer data_count;
    private Integer page_count;
    private Integer page_size;
}
