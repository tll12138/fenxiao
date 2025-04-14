package cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersaledata;

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
 * 分销退货传聚水潭明细中间 DO
 *
 * @author 管理员
 */
@TableName("fx_jst_after_sale_data")
@KeySequence("fx_jst_after_sale_data_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JstAfterSaleDataDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 主表id
     */
    private Long mainId;
    /**
     * 商品编码/商家商品编码
     */
    private String skuId;
    /**
     * 数量/退货数量
     */
    private Integer qty;
    /**
     * 批次单号，需开启配置
     */
    private String batchId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 有效期至，系统中需开启相关配置
     */
    private String expirationDate;
    /**
     * 商品单价
     */
    private BigDecimal salePrice;
    /**
     * 生产日期，系统中需开启相关配置
     */
    private String producedDate;
    /**
     * 平台订单明细编号
     */
    private String outerOiId;
    /**
     * SKU退款金额
     */
    private BigDecimal amount;
    /**
     * 可选: 退货，换货，其它，补发
     */
    private String type;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 图片地址
     */
    private String pic;
    /**
     * 属性规格
     */
    private String propertiesValue;
    /**
     * 标记数据来源，值为 2b 或 2c，对应具体主表
     */
    private String sourceType;
}