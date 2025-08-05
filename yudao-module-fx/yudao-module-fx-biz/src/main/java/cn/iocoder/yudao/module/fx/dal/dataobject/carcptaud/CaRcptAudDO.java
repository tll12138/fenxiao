package cn.iocoder.yudao.module.fx.dal.dataobject.carcptaud;

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
 * 客商账户收款审核 DO
 *
 * @author 管理员
 */
@TableName("fx_ca_rcpt_aud")
@KeySequence("fx_ca_rcpt_aud_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaRcptAudDO extends BaseDO {

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
     * <p>
     * 枚举 {@link TODO fx_fee_type 对应的类}
     */
    private Integer reason;
    /**
     * 支付方式
     * <p>
     * 枚举 {@link TODO fx_payment_method 对应的类}
     */
    private Integer payType;
    /**
     * 支付证明
     */
    private String payWarrant;
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
     * 提交人姓名
     */
    private String submiterName;
    /**
     * 打款账户
     */
    private String paymentAccount;
    /**
     * 客户等级
     * <p>
     * 枚举 {@link TODO fx_customer_level 对应的类}
     */
    private Integer custLevel;
    /**
     * 打款账户名称
     */
    private String paymentAccountName;
    /**
     * 是否重复
     * <p>
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private String isRepeat;
    /**
     * 业务主体
     * <p>
     * 枚举 {@link TODO fx_business_entity 对应的类}
     */
    private Integer businessEntity;
    /**
     * 申请日期
     */
    private String orderDate;
    /**
     * 申请日期
     */
    private String processInstanceId;
    /**
     * 是否节假日
     * <p>
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private String isWeek;

}