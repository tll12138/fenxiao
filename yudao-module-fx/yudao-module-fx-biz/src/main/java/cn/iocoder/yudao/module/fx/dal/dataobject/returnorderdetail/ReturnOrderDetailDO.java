package cn.iocoder.yudao.module.fx.dal.dataobject.returnorderdetail;

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
 * 销售退货详情 DO
 *
 * @author 管理员
 */
@TableName("fx_return_order_detail")
@KeySequence("fx_return_order_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnOrderDetailDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Long id;
    /**
     * 退货单id
     */
    private Long mainId;
    /**
     * 商品编码
     */
    private String skuId;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 规格
     */
    private String category;
    /**
     * 原销售价
     */
    private BigDecimal originalPrice;
    /**
     * 退货价
     */
    private BigDecimal returnPrice;
    /**
     * 成本价
     */
    private BigDecimal costPrice;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 退货金额
     */
    private BigDecimal saleAmt;
    /**
     * 成本金额
     */
    private BigDecimal costAmt;
    /**
     * 原单数量
     */
    private Integer originalCount;
    /**
     * 退货类型
     * <p>
     */
    private Integer retType;
    /**
     * 品牌
     */
    private String brand;

}