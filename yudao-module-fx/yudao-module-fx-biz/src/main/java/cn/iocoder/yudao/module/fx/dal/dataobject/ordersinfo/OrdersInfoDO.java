package cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 销售单 DO
 *
 * @author 管理员
 */
@TableName("fx_orders_info")
@KeySequence("fx_orders_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersInfoDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 流程实例id
     */
    private String processInstanceId;
    
    /**
     * 发货仓库
     */
    private String warehouse;
    /**
     * 发货仓库编码
     */
    private String warehouseCode;
    /**
     * 指定发货日期
     */
    private LocalDate specifySendDate;
    /**
     * erp单号
     */
    private String erpOrderNumber;
    /**
     * 是否传erp
     *
     * 枚举 {@link TODO fx_is_2_erp 对应的类}
     */
    private Integer isToErp;
    /**
     * 传erp时间
     */
    private LocalDate toErpTime;
    /**
     * 是否开票
     *
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private Integer isInvoice;

    /**
     * 收货地址ID
     */
    private Long addressId;

    /**
     * 省份
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 发货数量
     */
    private Integer sendQuantity;
    /**
     * 收货人
     */
    private String manager;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 单据编号
     */
    private String orderId;
    /**
     * 单据日期
     */
    private LocalDate orderDate;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 发货类型
     *
     * 枚举 {@link TODO fx_send_type 对应的类}
     */
    private Integer sendType;
    /**
     * 销售类型
     *
     * 枚举 {@link TODO fx_sale_type 对应的类}
     */
    private Integer salesType;
    /**
     * 销售商
     */
    private Long supplierId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 物流公司
     */
    private String logisticsCompany;
    /**
     * 物流单号
     */
    private String logisticsNumber;
    /**
     * 销售金额
     */
    private BigDecimal salesAmount;
    /**
     * 核算成本金额
     */
    private BigDecimal valTotAmount;
    /**
     * 标准金额
     */
    private BigDecimal standardAmount;
    /**
     * 平均折扣
     */
    private Double avgDis;
    /**
     * 发货日期
     */
    private LocalDate sendDate;
    /**
     * 发货时间
     */
    private LocalDate sendTime;
    /**
     * 退货状态
     */
    private Integer returnStatus;
    /**
     * 大客户地址
     */
    private String bigCustomerAddress;
    /**
     * 客户等级
     *
     * 枚举 {@link TODO fx_customer_level 对应的类}
     */
    private Integer customerLevel;
    /**
     * 商品合计
     */
    private String totalGoods;
    /**
     * 物流状态
     */
    private Integer logisticsStatus;
    /**
     * 总重量
     */
    private Double totalWeight;
    /**
     * 发货要求
     */
    private String requirement;
    /**
     * 品牌
     *
     * 枚举 {@link TODO fx_brand 对应的类}
     */
    private String brand;
    /**
     * 外部单号
     */
    private String externalOrderNumber;
    /**
     * 渠道
     */
    private Integer channel;
    /**
     * 订单类型
     *
     * 枚举 {@link TODO fx_sale_type 对应的类}
     */
    private Integer orderType;
    /**
     * 出库编号
     */
    private String outWarehouseNumber;
    /**
     * 是否新客寄样
     */
    private Integer isNew;
    /**
     * 是否无痕发货
     *
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private Integer isTraceless;
    /**
     * 分销商类型
     *
     * 枚举 {@link TODO fx_customer_type 对应的类}
     */
    private Integer customerType;
    /**
     * 复盘状态
     */
    private Integer replayStatus;
    /**
     * 提交日期
     */
    private LocalDate commitDate;
    /**
     * 是否扫码溯源
     */
    private Integer isScan;
    /**
     * 是否代发
     *
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private Integer isDf;
    /**
     * 客商代发属性
     */
    private Integer cusDfType;
    /**
     * 数据id
     */
    private Long dataId;
    /**
     * 收款经销商
     */
    private Long receiveSupplierId;
    /**
     * 收货方
     */
    private Long distributorId;
    /**
     * 业务归属
     *
     * 枚举 {@link TODO fx_belong 对应的类}
     */
    private Integer businessBelong;

}