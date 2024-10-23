package cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail;

import com.sun.xml.bind.v2.TODO;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
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
 * 分销-销售订单明细 DO
 *
 * @author 管理员
 */
@TableName("fx_orders_detail")
@KeySequence("fx_orders_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDetailDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 主表订单id
     */
    private Long orderId;
    /**
     * 销售类型
     *
     * 枚举 {@link TODO fx_detail_return_type 对应的类}
     */
    private Integer saleType;
    /**
     * 商品编码
     */
    private String skuId;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 商品规格
     */
    private String category;
    /**
     * 销售价
     */
    private BigDecimal price;
    /**
     * 结算价
     */
    private BigDecimal salePrice;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 成本价
     */
    private BigDecimal costPrice;
    /**
     * 销售金额
     */
    private BigDecimal priceAmount;
    /**
     * 销售金额
     */
    private BigDecimal saleAmount;
    /**
     * 成本金额
     */
    private BigDecimal costAmount;
    /**
     * 退货数量
     */
    private Integer returnCount;
    /**
     * 退货标志
     */
    private String returnFlag;
    /**
     * 最低销售价
     */
    private BigDecimal lowPrice;
    /**
     * 商品净重(kg)
     */
    private BigDecimal goodsWeight;
    /**
     * 其他仓库可用库存
     */
    private Integer otherAvailCount;

}