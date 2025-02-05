package cn.iocoder.yudao.module.fx.dal.dataobject.inventorydata;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 分销商品库存 DO
 *
 * @author 管理员
 */
@TableName("fx_inventory_data")
@KeySequence("fx_inventory_data_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDataDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Integer id;
    /**
     * 商品编码
     */
    private String skuId;
    /**
     * 条形码
     */
    private String scanCode;
    /**
     * 商品资料id
     */
    private String goodId;
    /**
     * 良品库存
     */
    private BigDecimal qty;
    /**
     * 次品库存
     */
    private BigDecimal defectiveQty;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 仓库类型
     */
    private BigDecimal type;
    /**
     * 所属仓库
     */
    private String warehouseId;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 销售出库数
     */
    private BigDecimal saleQty;
    /**
     * 数据渠道
     */
    private String channel;
    /**
     * 商品品牌
     */
    private String brand;
    /**
     * 规格
     */
    private String value;
    /**
     * 商品名称
     */
    private String name;

}