package cn.iocoder.yudao.module.fx.dal.dataobject.importorder;

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
 * 客商代发单 DO
 *
 * @author 管理员
 */
@TableName("fx_import_order")
@KeySequence("fx_import_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportOrderDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 商品数量
     */
    private BigDecimal productQuantity;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 订单编号
     */
    private String soId;
    /**
     * 商品编码
     */
    private String skuId;
    /**
     * 区县
     */
    private String district;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 收货人
     */
    private String contact;
    /**
     * 收货电话
     */
    private String mobile;
    /**
     * 是否发货
     * <p>
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private Integer isShipped;
    /**
     * 是否生成销售单
     * <p>
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private Integer isSalesOrderGenerated;
    /**
     * 客商
     */
    private String customerid;
    /**
     * 客商名称
     */
    private String customername;
    /**
     * 发货仓名称
     */
    private String warehousename;
    /**
     * 发货仓
     */
    private String warehouseid;
    /**
     * 销售单号
     */
    private String saleno;
    /**
     * 快递公司
     */
    private String expressCompany;
    /**
     * 快递单号
     */
    private String trackingNumber;
    /**
     * 备注
     */
    private String remark;
    /**
     * 业务归属
     */
    private Integer businessAffiliation;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 无痕发货
     */
    private Integer isTraceless;
    /**
     * 收款经销商
     */
    private Integer payingDistributorId;
    /**
     * 快递公司ID
     */
    private String expressCompanyId;

}