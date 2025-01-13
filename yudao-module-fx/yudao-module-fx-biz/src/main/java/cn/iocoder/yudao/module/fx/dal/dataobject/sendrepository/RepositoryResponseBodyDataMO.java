package cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class RepositoryResponseBodyDataMO {
    private String requestId;
    @JSONField(name = "page_index")
    private int pageIndex;
    //是否有下一页
    @JSONField(name = "has_next")
    private boolean hasNext;
    @JSONField(name = "data_count")
    private int dataCount;
    @JSONField(name = "page_count")
    private int pageCount;
    @JSONField(name = "page_size")
    private int pageSize;
    @JSONField(name = "datas")
    private List<SendRepositoryVO> datas;
}
