package cn.iocoder.yudao.module.fx.dal.dataobject.monsettlement;

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
 * 分销账户月结 DO
 *
 * @author 管理员
 */
@TableName("fx_mon_settlement")
@KeySequence("fx_mon_settlement_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonSettlementDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Integer id;
    /**
     * 年月
     */
    private String month;
    /**
     * 月结总余额
     */
    private BigDecimal totalAmount;
    /**
     * 暂扣金额
     */
    private BigDecimal withheldAmount;
    /**
     * 可用余额
     */
    private BigDecimal availableAmount;
    /**
     * 账户
     */
    private String account;
    /**
     * 账户名称
     */
    private String accountName;
    /**
     * 业务主体
     */
    private String company;

}