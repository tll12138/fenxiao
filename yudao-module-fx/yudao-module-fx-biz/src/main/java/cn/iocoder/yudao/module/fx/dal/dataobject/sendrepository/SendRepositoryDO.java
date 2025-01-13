package cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

/**
 * FX 发货仓库 DO
 *
 * @author 管理员
 */
@TableName("fx_send_repository")
@KeySequence("fx_send_repository_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendRepositoryDO extends BaseDO {

    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableId
    private Integer id;
    /**
     * 仓库名称
     */
    private String name;
    /**
     * 仓库类型
     * <p>
     * 枚举 {@link TODO fx_repository_type 对应的类}
     */
    private Integer type;

    /**
     * 仓库编码
     */
    private String code;

    /**
     * 备注
     */
    private String remark;
    /**
     * 仓库全称
     */
    private String allName;
    /**
     * 是否传erp
     */
    private Integer isToErp;
    /**
     * 主仓公司编号
     */
    private String coId;
    /**
     * 是否可用
     */
    private Integer isUsed;
    /**
     * 是否内部仓
     */
    private Integer isInside;
    /**
     * 渠道
     * <p>
     * 枚举 {@link TODO fx_respository_channel 对应的类}
     */
    private Integer channel;

}