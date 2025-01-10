package cn.iocoder.yudao.module.fx.dal.dataobject.goodsarchives;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 分销商品资料 DO
 *
 * @author 管理员
 */
@TableName("fx_goods_archives")
@KeySequence("fx_goods_archives_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsArchivesDO extends BaseDO {

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
     * 商品名称
     */
    private String name;
    /**
     * 商品简称
     */
    private String shortName;
    /**
     * 标准价
     */
    private BigDecimal salePrice;
    /**
     * 成本价
     */
    private BigDecimal costPrice;
    /**
     * 规格
     */
    private String propertiesValue;
    /**
     * 分类
     */
    private String category;
    /**
     * 虚拟分类
     */
    private String vcName;
    /**
     * 商品属性
     */
    private String itemType;
    /**
     * 单位
     */
    private String unit;
    /**
     * 商品类型
     */
    private String skuType;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 开票名称
     */
    private String billingName;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 商品图片
     */
    private String picBig;
    /**
     * 款式编码
     */
    private String iId;
    /**
     * 市场价
     */
    private BigDecimal marketPrice;
    /**
     * 是否启用
     */
    private String enabled;
    /**
     * 是否分销商品
     */
    private String isFx;
    /**
     * 实际成本价
     */
    private BigDecimal actualCostPrice;
    /**
     * 销售成本价
     */
    private BigDecimal saleCostPrice;
    /**
     * 一级分类
     */
    private String level1Category;
    /**
     * 二级分类
     */
    private String level2Category;
    /**
     * 是否参与计算
     */
    private String isCount;
    /**
     * 是否正装
     */
    private String isFormal;
    /**
     * 净重(kg)
     */
    private BigDecimal weight;
    /**
     * 国标码
     */
    private String scancode;
    /**
     * 是否组合商品
     */
    private String isGroup;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 聚水潭唯一id，系统自增id
     */
    private Integer autoId;

}