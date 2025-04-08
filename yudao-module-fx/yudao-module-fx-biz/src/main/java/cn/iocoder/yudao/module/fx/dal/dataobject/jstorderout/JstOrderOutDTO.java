package cn.iocoder.yudao.module.fx.dal.dataobject.jstorderout;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 聚水潭发货回传中间表 DO
 *
 * @author 管理员
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JstOrderOutDTO extends BaseDO {

    /**
     * 内部单号
     */
    private Integer oId;
    /**
     * 销售单号
     */
    private String soId;
    /**
     * 快递公司
     */
    private String expressName;
    /**
     * 快递单号
     */
    private String express;
    /**
     * 快递编码
     */
    private String expressCode;
    /**
     * 销售单来源
     */
    private String soFrom;
    /**
     * 收件人
     */
    private String receiverName;
    /**
     * 分销商名称
     */
    private String displayName;

}