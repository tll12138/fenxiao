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
 * 发票申请详情 DO
 *
 * @author 管理员
 */
@TableName("fx_bill_apply_detail")
@KeySequence("fx_bill_apply_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillApplyDetailDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Integer id;
    /**
     * 主表id
     */
    private Integer mainId;
    /**
     * 商品名称
     */
    private String vareName;
    /**
     * 规格型号
     */
    private String wareSpec;
    /**
     * 计量单位
     */
    private String wareUnit;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 税率
     */
    private BigDecimal payment;
    /**
     * 税额
     */
    private BigDecimal salTax;
    /**
     * 货补数量
     */
    private Integer hbNum;
    /**
     * 货补数量
     */
    private Integer saleOrderId;

}