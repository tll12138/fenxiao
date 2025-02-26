package cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tll
 * @date 2025-02-26 09:51:27
 */
@Data
public class OrderUploadResData {
    @JsonProperty("datas")
    /* 结果集 */
    private List<ResultData> datas;
}
