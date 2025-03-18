package cn.iocoder.yudao.module.fx.dal.dataobject.accountcollection;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

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
     *
     * 枚举 {@link TODO yes_no 对应的类}
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
     *
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private String isWeek;

}