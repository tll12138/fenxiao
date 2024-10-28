package cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository;

import com.sun.xml.bind.v2.TODO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

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
     *
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

}