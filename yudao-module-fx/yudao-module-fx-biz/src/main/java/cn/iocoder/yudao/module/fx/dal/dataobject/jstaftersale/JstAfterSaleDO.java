package cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale;

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
 * 分销退货传聚水潭中间 DO
 *
 * @author 管理员
 */
@TableName("fx_jst_after_sale")
@KeySequence("fx_jst_after_sale_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JstAfterSaleDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 店铺编号
     */
    private Long shopId;
    /**
     * 退货退款单号，平台唯一
     */
    private String outerAsId;
    /**
     * 平台订单号
     */
    private String soId;
    /**
     * 售后类型，普通退货，其它，拒收退货,仅退款,投诉,补发,换货 或 出入库类型:in是入库（其它退货）out是出库（其它出库）
     */
    private String type;
    /**
     * 快递公司
     */
    private String logisticsCompany;
    /**
     * 物流单号
     */
    private String lId;
    /**
     * WAIT_SELLER_AGREE:买家已经申请退款，等待卖家同意,WAIT_BUYER_RETURN_GOODS:卖家已经同意退款，等待买家退货,WAIT_SELLER_CONFIRM_GOODS:买家已经退货，等待卖家确认收货,SELLER_REFUSE_BUYER:卖家拒绝退款,CLOSED:退款关闭(售后单未确认前填写该状态erp的售后单自动作废),SUCCESS:退款成功；可更新，补发、换货售后单确认时需在系统中手动确认
     */
    private String shopStatus;
    /**
     * 备注
     */
    private String remark;
    /**
     * BUYER_NOT_RECEIVED:买家未收到货,BUYER_RECEIVED:买家已收到货,BUYER_RETURNED_GOODS:买家已退货,SELLER_RECEIVED:卖家已收到退货；可更新
     */
    private String goodStatus;
    /**
     * 问题类型；可更新
     */
    private String questionType;
    /**
     * 原单据总金额
     */
    private BigDecimal totalAmount;
    /**
     * 卖家应退金额
     */
    private BigDecimal refund;
    /**
     * 买家应补偿金额
     */
    private BigDecimal payment;
    /**
     * 订单状态0待推送，1推送成功，2推送失败,3入库成功,4回写成功 或 传erp状态，0未传，1成功，2失败
     */
    private Integer orderStatus;
    /**
     * 内部售后单号
     */
    private Long asId;
    /**
     * 内部订单号
     */
    private Long oId;
    /**
     * 是否自动确认状态0无需确认，1需要自动确认，2自动确认失败,3自动确认成功 或 是否确认，0否，1是
     */
    private Integer isConfirm;
    /**
     * 收货仓编码 或 分仓编号或者三方仓编码
     */
    private Long wmsCoId;
    /**
     * 仓库类型；主仓 = 1, 销退仓 = 2, 进货仓 = 3, 次品仓 = 4, 门店 = 5, 自定义1仓=6，自定义2仓=7, 自定义3仓=8
     */
    private Integer warehouseType;
    /**
     * 收货人城市
     */
    private String receiverCity;
    /**
     * 收货人区县
     */
    private String receiverDistrict;
    /**
     * 外部单号（单据上传成功之后对应页面线上单号）
     */
    private String externalId;
    /**
     * 出库类型
     */
    private String drpCoName;
    /**
     * 默认1 ，主仓=1，销退仓=2，进货仓=3，次品仓=4，自定义1仓=6，自定义2仓=7，自定义3仓=8，自定义4仓=9，自定义5仓=10，自定义6仓=11，自定义7仓=12，自定义8仓=13，自定义9仓=14，自定义10仓=15（对应ERP仓库资料设定页面）
     */
    private Integer warehouse;
    /**
     * 收货人电话
     */
    private String receiverMobile;
    /**
     * 收货人省
     */
    private String receiverState;
    /**
     * 收货人名称
     */
    private String receiverName;
    /**
     * 收货地址
     */
    private String receiverAddress;
    /**
     * 标签
     */
    private String labels;
    /**
     * 传erp时间
     */
    private String toErpTime;
    /**
     * 传erp状态，0未传，1成功，2失败
     */
    private Integer toErpStatus;
    /**
     * 传erp日志
     */
    private String toErpMsg;
    /**
     * 订单来源
     */
    private String orderFrom;
    /**
     * 是否审核单据；0否1是
     */
    private Integer excuteConfirming;
    /**
     * 物流公司编码
     */
    private String lcId;
    /**
     * 聚水潭系统内部单号（单据上传成功之后对应页面出仓单号）
     */
    private Long ioId;
    /**
     * 标记数据来源，值为 2b 或 2c，对应传聚水潭的接口
     */
    private String sourceType;
}