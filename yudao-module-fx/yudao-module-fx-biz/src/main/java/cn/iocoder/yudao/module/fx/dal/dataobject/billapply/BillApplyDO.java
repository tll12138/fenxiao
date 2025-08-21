package cn.iocoder.yudao.module.fx.dal.dataobject.billapply;

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
 * 发票申请 DO
 *
 * @author 管理员
 */
@TableName("fx_bill_apply")
@KeySequence("fx_bill_apply_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillApplyDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Integer id;
    /**
     * 申请人
     */
    private BigDecimal applyMan;
    /**
     * 申请日期
     */
    private String applyDate;
    /**
     * 开票日期
     */
    private String billDate;
    /**
     * 地址及电话
     */
    private String address;
    /**
     * 开户行及账号
     */
    private String bankNo;
    /**
     * 金额合计
     */
    private BigDecimal amount;
    /**
     * 操作员
     */
    private BigDecimal maker;
    /**
     * 备注
     */
    private String remark;
    /**
     * 发票类型
     * <p>
     */
    private BigDecimal billType;
    /**
     * 金额合计(大写)
     */
    private String totalAmount;
    /**
     * 销售单
     */
    private String saleOrder;
    /**
     * 发票抬头
     * <p>
     */
    private String billHead;
    /**
     * 发票附件
     */
    private String document;
    /**
     * 财务说明
     */
    private String financialStatement;
    /**
     * 购方名称
     */
    private String purchaserName;
    /**
     * 纳税人识别号
     */
    private String taxNo;
    /**
     * 客户名称
     */
    private String cusName;
    /**
     * 开票信息
     */
    private String billInfo;
    /**
     * 开票流程
     */
    private String rid;
    /**
     * 是否完成
     * <p>
     */
    private BigDecimal isOver;
    /**
     * 是否芽肌
     * <p>
     */
    private BigDecimal isYj;
    /**
     * 发票邮箱
     */
    private String email;
    /**
     * 关联邮箱
     */
    private String emailId;
    /**
     * 发票发送状态
     */
    private BigDecimal isSend;

}