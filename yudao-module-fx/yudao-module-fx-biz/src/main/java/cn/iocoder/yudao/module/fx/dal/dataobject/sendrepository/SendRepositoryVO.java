package cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 聚水潭商品资料
 *
 * @author tll
 */
@Data
public class SendRepositoryVO extends BaseDO {

    /**
     * 分仓名称
     */
    private String name;
    /**
     * 主仓公司编号
     */
    @JSONField(name = "co_id")
    private Integer coId;
    /**
     * 分仓编号
     */
    @JSONField(name = "wms_co_id")
    private Integer code;
    /**
     * 是否为主仓，true=主仓
     */
    @JSONField(name = "is_main")
    private Boolean isMain;
    /**
     * 状态
     */
    private String status;
    /**
     * 对方备注
     */
    private String remark1;
    /**
     * 我方备注
     */
    @JSONField(name = "remark2")
    private String remark;

}