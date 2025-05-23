package cn.iocoder.yudao.module.fx.dal.dataobject.fromaccount;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 分销打款账户 DO
 *
 * @author 管理员
 */
@TableName("fx_from_account")
@KeySequence("fx_from_account_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FromAccountDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Integer id;
    /**
     * 打款账户号
     */
    private String account;
    /**
     * 所属分销商
     */
    private String customerId;
    /**
     * 所属分销商名称
     */
    private String customerName;
    /**
     * 账户类型
     */
    private Integer accountType;
    /**
     * 说明
     */
    private String remark;
    /**
     * 是否有效
     * <p>
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private String isActive;
    /**
     * 累计打款次数
     */
    private Integer totalNum;
    /**
     * 累计打款金额
     */
    private BigDecimal totalAmt;
    /**
     * 打款方名称
     */
    private String accountName;
    /**
     * 所属账户名称
     */
    private String accountId;

}