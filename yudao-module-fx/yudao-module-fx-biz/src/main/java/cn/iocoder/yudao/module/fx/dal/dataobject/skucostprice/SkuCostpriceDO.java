package cn.iocoder.yudao.module.fx.dal.dataobject.skucostprice;

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
 * 商品成本 DO
 *
 * @author 管理员
 */
@TableName("fx_sku_costprice")
@KeySequence("fx_sku_costprice_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuCostpriceDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Long id;
    /**
     * 品牌
     *
     * 枚举 {@link TODO fx_brand 对应的类}
     */
    private String brand;
    /**
     * 商品编码
     */
    private String skuId;
    /**
     * 名称
     */
    private String name;
    /**
     * 规格
     */
    private String value;
    /**
     * 品类
     */
    private BigDecimal type;
    /**
     * 属性
     */
    private BigDecimal paid;
    /**
     * 财务结算价
     */
    private BigDecimal costPrice;
    /**
     * 采购成本
     */
    private BigDecimal costOtherprice;
    /**
     * 出库成本
     */
    private BigDecimal outCost;

}