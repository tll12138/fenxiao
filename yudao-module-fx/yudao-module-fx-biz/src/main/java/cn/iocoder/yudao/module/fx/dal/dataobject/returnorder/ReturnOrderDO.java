package cn.iocoder.yudao.module.fx.dal.dataobject.returnorder;

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
 * FX 销售退货单 DO
 *
 * @author 员工
 */
@TableName("fx_return_order")
@KeySequence("fx_return_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnOrderDO extends BaseDO {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    /**
     * 单据编号
     */
    private String orderId;
    /**
     * 单据日期
     */
    private LocalDate orderDate;
    /**
     * 单据状态
     *
     * 枚举 {@link TODO fx_order_status 对应的类}
     */
    private Integer orderStatus;
    /**
     * 退货类型
     *
     * 枚举 {@link TODO fx_return_type 对应的类}
     */
    private Integer returnType;
    /**
     * 原销售单
     */
    private String originOrder;
    /**
     * 退货方
     */
    private Long returnUserId;
    /**
     * 退货经销商
     */
    private Long returnDealer;
    /**
     * 收货经销商
     */
    private Long receiveDealer;
    /**
     * 收货仓库
     */
    private String warehouse;
    /**
     * 仓库功能
     *
     * 枚举 {@link TODO fx_warehouse_feature 对应的类}
     */
    private String warehouseFeature;
    /**
     * 物流单号
     */
    private String logisticsNumber;
    /**
     * 物流公司
     */
    private String logisticsCompany;
    /**
     * 发货仓库编码
     */
    private String warehouseCode;
    /**
     * 退货业务类型
     *
     * 枚举 {@link TODO fx_business_type 对应的类}
     */
    private Integer returnBusinessType;
    /**
     * 原单数量
     */
    private Integer originQuantity;
    /**
     * 备注
     */
    private String remark;
    /**
     * 总退货金额
     */
    private BigDecimal totalReturnAmount;
    /**
     * 总退货数量
     */
    private Integer totalReturnQuantity;
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

}