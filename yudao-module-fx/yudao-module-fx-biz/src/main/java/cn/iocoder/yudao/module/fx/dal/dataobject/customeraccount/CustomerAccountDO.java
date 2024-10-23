package cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

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
     * 业务主体
     */
    private Long company;
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
    private BigDecimal deposit;

}