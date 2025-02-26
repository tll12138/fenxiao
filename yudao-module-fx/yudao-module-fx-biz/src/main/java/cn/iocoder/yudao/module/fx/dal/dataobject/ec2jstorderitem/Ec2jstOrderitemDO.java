package cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorderitem;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 分销订单上传详情中间 DO
 *
 * @author 管理员
 */
@TableName("fx_ec2jst_orderitem")
@KeySequence("fx_ec2jst_orderitem_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ec2jstOrderitemDO extends BaseDO {

    /**
     * 商品编码
     */
    private String skuiId;
    /**
     * 店铺商品编码
     */
    private String shopSkuId;
    /**
     * erp款号
     */
    private String iId;
    /**
     * 图片地址
     */
    private String pic;
    /**
     * 商品属性
     */
    private String propertiesValue;
    /**
     * 成交总额
     */
    private BigDecimal amount;
    /**
     * 原价
     */
    private BigDecimal basePrice;
    /**
     * 数量
     */
    private BigDecimal qty;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 状态
     */
    private String refundStatus;
    /**
     * 出库oid
     */
    private String outerOiId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 订单表id
     */
    private Integer mainid;

}