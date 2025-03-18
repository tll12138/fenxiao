package cn.iocoder.yudao.module.fx.dal.dataobject.accountadjust;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 分销账户调整 DO
 *
 * @author 管理员
 */
@TableName("fx_account_adjust")
@KeySequence("fx_account_adjust_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountAdjustDO extends BaseDO {

    /**
     * 序号
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
     * 调整分销账户
     */
    private String account;
    /**
     * 调整说明
     */
    private String remark;
    /**
     * 提交人
     */
    private String submiter;
    /**
     * 调整金额
     */
    private BigDecimal adjAmount;
    /**
     * 调整后账户余额
     */
    private BigDecimal afterAmount;
    /**
     * 当前账户余额
     */
    private BigDecimal nowAmount;
    /**
     * 业务主体
     */
    private String company;
    /**
     * 当前暂扣账户金额
     */
    private BigDecimal nowTempAmount;
    /**
     * 调整后暂扣账户金额
     */
    private BigDecimal afterTempAmount;
    /**
     * 调整类型
     */
    private String type;

}