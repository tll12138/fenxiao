package cn.iocoder.yudao.module.fx.dal.dataobject.accountcollection;

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
import java.time.LocalDateTime;

/**
 * 分销账户收款记录 DO
 *
 * @author 管理员
 */
@TableName("fx_account_collection")
@KeySequence("fx_account_collection_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCollectionDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 单据编号
     */
    private String orderNo;
    /**
     * 费用类型
     */
    private String reason;
    /**
     * 支付方式
     */
    private String payType;
    /**
     * 支付证明
     */
    private String payProof;
    /**
     * 实际账户
     */
    private String account;
    /**
     * 实际账户名称
     */
    private String accountName;
    /**
     * 收款金额
     */
    private BigDecimal receive;
    /**
     * 备注
     */
    private String remark;
    /**
     * 分销商
     */
    private String customer;
    /**
     * 分销商名称
     */
    private String customerName;
    /**
     * 业务单据
     */
    private String soId;
    /**
     * 提交人
     */
    private String submiter;
    /**
     * 打款账户
     */
    private String payoutAccountId;
    /**
     * 客户等级
     */
    private String level;
    /**
     * 打款账户名称
     */
    private String payoutAccountName;
    /**
     * 是否重复
     */
    private String isRepeat;
    /**
     * 业务主体
     */
    private String mainId;
    /**
     * 申请日期
     */
    private LocalDateTime orderDate;
    /**
     * 是否周末
     */
    private String isWeek;

}