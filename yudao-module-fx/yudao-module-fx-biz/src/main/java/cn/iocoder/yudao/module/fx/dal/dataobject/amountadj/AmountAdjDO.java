package cn.iocoder.yudao.module.fx.dal.dataobject.amountadj;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 分销账户资金调整记录 DO
 *
 * @author 管理员
 */
@TableName("fx_amount_adj")
@KeySequence("fx_amount_adj_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmountAdjDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Integer id;
    /**
     * 流程编号
     */
    private String soId;
    /**
     * 调整金额
     */
    private BigDecimal amount;
    /**
     * 调整日期
     */
    private String orderDate;
    /**
     * 调整账户
     */
    private String account;
    /**
     * 调整说明
     */
    private String remark;
    /**
     * 调整类型
     */
    private Integer type;
    /**
     * 调整后余额
     */
    private BigDecimal adjustBalance;

}