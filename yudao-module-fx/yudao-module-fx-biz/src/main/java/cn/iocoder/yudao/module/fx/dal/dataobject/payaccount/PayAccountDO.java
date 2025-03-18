package cn.iocoder.yudao.module.fx.dal.dataobject.payaccount;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 分销支付账户 DO
 *
 * @author 管理员
 */
@TableName("fx_pay_account")
@KeySequence("fx_pay_account_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayAccountDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Integer id;
    /**
     * 类型
     *
     * 枚举 {@link TODO account_type 对应的类}
     */
    private Integer payType;
    /**
     * 分销商账户
     */
    private String customerId;
    /**
     * 付款账户
     */
    private String accountNo;
    /**
     * 说明
     */
    private String description;
    /**
     * 是否可用
     *
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private String isActive;

}