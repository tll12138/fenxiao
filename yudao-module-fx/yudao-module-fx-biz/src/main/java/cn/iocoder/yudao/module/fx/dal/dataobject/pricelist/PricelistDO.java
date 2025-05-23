package cn.iocoder.yudao.module.fx.dal.dataobject.pricelist;

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
 * 分销价格对照 DO
 *
 * @author 管理员
 */
@TableName("fx_pricelist")
@KeySequence("fx_pricelist_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PricelistDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Integer id;
    /**
     * 客户
     */
    private String customer;
    /**
     * 产品编码
     */
    private String skuId;
    /**
     * 规格
     */
    private String category;
    /**
     * 销售最低价
     */
    private BigDecimal saleprice;
    /**
     * 客户等级
     */
    private Integer distributorLevel;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 是否基础类型
     */
    private String isNormal;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 客户id
     */
    private Integer customerId;
    /**
     * 品牌id
     */
    private String brandId;

}