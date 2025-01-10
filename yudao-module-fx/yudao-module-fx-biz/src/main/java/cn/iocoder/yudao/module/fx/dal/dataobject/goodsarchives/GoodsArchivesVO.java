package cn.iocoder.yudao.module.fx.dal.dataobject.goodsarchives;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 聚水潭商品资料
 *
 * @author tll
 */
@Data
public class GoodsArchivesVO extends BaseDO {

    /**
     * 商品编码
     */
    @JSONField(name = "sku_id")
    private String skuId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品简称
     */
    @JSONField(name = "short_name")
    private String shortName;
    /**
     * 标准价
     */
    @JSONField(name = "sale_price")
    private BigDecimal salePrice;
    /**
     * 成本价
     */
    @JSONField(name = "cost_price")
    private BigDecimal costPrice;
    /**
     * 规格
     */
    @JSONField(name = "properties_value")
    private String propertiesValue;
    /**
     * 分类
     */
    private String category;
    /**
     * 虚拟分类
     */
    @JSONField(name = "vc_name")
    private String vcName;
    /**
     * 商品属性
     */
    @JSONField(name = "item_type")
    private String itemType;
    /**
     * 单位
     */
    private String unit;
    /**
     * 商品类型
     */
    @JSONField(name = "sku_type")
    private String skuType;
    /**
     * 修改时间
     */
    @JSONField(name = "modified")
    private LocalDateTime updateTime;
    /**
     * 开票名称
     */
    @JSONField(name = "has_next")
    private String billingName;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 商品图片
     */
    @JSONField(name = "pic_big")
    private String picBig;
    /**
     * 款式编码
     */
    @JSONField(name = "i_id")
    private String iId;
    /**
     * 市场价
     */
    @JSONField(name = "market_price")
    private BigDecimal marketPrice;
    /**
     * 是否启用
     */
    @JSONField(name = "enabled")
    private String enabled;
    /**
     * 净重(kg)
     */
    private BigDecimal weight;
    /**
     * 国标码
     */
    @JSONField(name = "sku_code")
    private String scancode;
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
    @JSONField(name = "autoid")
    private Integer autoId;

}