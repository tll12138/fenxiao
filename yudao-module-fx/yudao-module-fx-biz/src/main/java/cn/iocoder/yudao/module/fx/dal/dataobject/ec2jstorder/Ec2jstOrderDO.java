package cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 分销订单上传中间 DO
 *
 * @author 管理员
 */
@TableName("fx_ec2jst_order")
@KeySequence("fx_ec2jst_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ec2jstOrderDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Integer id;
    /**
     * 店铺编码
     */
    private Integer shopId;
    /**
     * 下单时间
     */
    private String orderDate;
    /**
     * 店铺状态
     */
    private String shopStatus;
    /**
     * 买家账号
     */
    private String shopBuyerId;
    /**
     * 省
     */
    private String receiverState;
    /**
     * 市
     */
    private String receiverCity;
    /**
     * 区
     */
    private String receiverDistrict;
    /**
     * 地址
     */
    private String receiverAddress;
    /**
     * 收件人
     */
    private String receiverName;
    /**
     * 联系电话
     */
    private String receiverPhone;
    /**
     * 手机
     */
    private String receiverMobile;
    /**
     * 应付金额
     */
    private BigDecimal payAmount;
    /**
     * 运费
     */
    private BigDecimal freight;
    /**
     * 卖家备注
     */
    private String remark;
    /**
     * 是否货到付款
     */
    private String isCod;
    /**
     * 修改日期
     */
    private String shopModified;
    /**
     * 快递单号
     */
    private String lId;
    /**
     * 快递公司名称
     */
    private String logisticsCompany;
    /**
     * 订单异常描述
     */
    private String questionDesc;
    /**
     * 卖家标签（旗帜）
     */
    private String sellerFlag;
    /**
     * 快递公司编码
     */
    private String lcId;
    /**
     * 传ERP时间
     */
    private String toErpTime;
    /**
     * 传输标记
     */
    private String erpStatus;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 聚水潭内部单号
     */
    private Integer oId;
    /**
     * 订单状态 2取消 3取消成功 4取消失败
     */
    private Integer orderStatus;
    /**
     * 仓库中文名称（作为聚水潭标签传值）
     */
    private String warehouse;
    /**
     * SALE销售单,MARKET营销寄样,ZP_ADJUST内部仓正品调整,CP_ADJUST内部仓次品调整
     */
    private String soFrom;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 调入仓公司内仓库id，主仓=1，销退仓=2，进货仓=3，次品仓=4，自定义1仓=6，自定义2仓=7，自定义3仓=8（对应ERP仓库资料设定页面）
     */
    private String linkWarehouse;
    /**
     * 调出仓公司内仓库id，主仓=1，销退仓=2，进货仓=3，次品仓=4，自定义1仓=6，自定义2仓=7，自定义3仓=8（对应ERP仓库资料设定页面）
     */
    private String warehouseId;
    /**
     * 内部调拨单号ID
     */
    private String allocateInId;
    /**
     * 是否出库 1已出库，0没有，2出库失败
     */
    private Integer isOut;
    /**
     * 失败重试次数
     */
    private BigDecimal errorNum;

}