package cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 分销商账号 DO
 *
 * @author 管理员
 */
@TableName("fx_customer_account")
@KeySequence("fx_customer_account_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAccountDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 分销商编号
     */
    private Long distributorId;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 账户编号
     */
    private String accountId;
    /**
     * 暂扣金额
     */
    private BigDecimal detainAmount;
    /**
     * 是否冻结
     */
    private Integer isActive;
    /**
     * 押金
     */
    private Integer deposit;
    /**
     * 业务主体
     */
    private Integer company;
    /**
     * 货补虚拟金额
     */
    private BigDecimal vAmount;
    /**
     * 是否允许超额提货（0否1是）
     */
    private Integer isAllow;
    /**
     * 超额提货额度
     */
    private BigDecimal quota;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否计算货补（0否1是）
     */
    private Integer isRep;
    /**
     * 暂扣货补金额
     */
    private BigDecimal zkVAmount;
    /**
     * 账户名
     */
    private String name;

}