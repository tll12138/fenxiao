package cn.iocoder.yudao.module.fx.dal.dataobject.manualdelivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tll
 * @date 2025-04-02 14:06:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryResponseDataItemDo {
    /**
     * 快递单已打印
     */
    private Boolean is_print_express;
    /**
     * 平台买家唯一值（仅支持天猫，抖音，快手）
     */
    private String open_id;
    /**
     * 实称重量
     */
    private Integer f_weight;
    /**
     * 分销商编号
     */
    private String drp_co_id_from;
    /**
     * 业务人员
     */
    private String business_staff;
    /**
     * 拣货批次号
     */
    private Integer wave_id;
    /**
     * 修改时间（格式：2021-09-2315:07:58）
     */
    private String modified;
    /**
     * 收件国家
     */
    private String receiver_country;
    /**
     * 订单已打印
     */
    private Boolean is_print;
    /**
     * 订单类型（普通订单/补发订单/分销Plus/供销Plus/换货订单）
     */
    private String order_type;
    /**
     * 订单业务员编号
     */
    private Integer order_staff_id;
    /**
     * 买家留言
     */
    private String buyer_message;
    /**
     * 创建时间（格式：2021-09-1614:05:07）
     */
    private String created;
    /**
     * 预估重量
     */
    private Double weight;
    /**
     * 店铺名称
     */
    private String shop_name;
    /**
     * 出库单号（商家维度下出库单全局唯一值）
     */
    private Integer io_id;
    /**
     * 标记（多标签，用竖线分隔）
     */
    private String labels;
    /**
     * 店铺编码
     */
    private Integer shop_id;
    /**
     * 买家昵称/线下客户
     */
    private String shop_buyer_id;
    /**
     * 分仓编号
     */
    private Integer wms_co_id;
    /**
     * 实付金额
     */
    private Double paid_amount;
    /**
     * 是否启用库存管理（on/off）
     */
    private String stock_enabled;
    /**
     * 订单状态：
     * WaitConfirm-待出库,
     * Confirmed-已出库,
     * Delete-作废（订单发货中取消）,
     * OuterConfirming-外部发货中,
     * Cancelled-取消（发货后撤销）
     */
    private String status;
    /**
     * 付款日期（格式：2019-12-1616:35:28）
     */
    private String pay_date;
    /**
     * 快递公司编码
     */
    private String lc_id;
    /**
     * 发票抬头
     */
    private String invoice_title;
    /**
     * 收件人市
     */
    private String receiver_city;
    /**
     * 买家支付运费
     */
    private Double freight;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否货到付款
     */
    private Boolean is_cod;
    /**
     * 收件人省
     */
    private String receiver_state;
    /**
     * 线上单号
     */
    private String so_id;
    /**
     * 订单业务员名称
     */
    private String order_staff_name;
    /**
     * 内部单号
     */
    private Integer o_id;
    /**
     * 收件人姓名
     */
    private String receiver_name;
    /**
     * 货币类型
     */
    private String currency;
    /**
     * 优惠金额
     */
    private Double free_amount;
    /**
     * 出库时间（格式：2021-09-2315:07:58）
     */
    private String io_date;
    /**
     * 收件人区
     */
    private String receiver_district;
    /**
     * 收件人街道
     */
    private String receiver_town;
    /**
     * 应付金额
     */
    private Double pay_amount;
    /**
     * 公司编号
     */
    private Integer co_id;
    /**
     * 收件人手机
     */
    private String receiver_mobile;
    /**
     * 旗帜（1=红旗，2=黄旗，3=绿旗，4=蓝旗，5=紫旗）
     */
    private Integer seller_flag;
    /**
     * 合并订单号
     */
    private String merge_so_id;
    /**
     * 收件地址
     */
    private String receiver_address;
    /**
     * 物流公司名称
     */
    private String logistics_company;
    /**
     * 收件人电话
     */
    private String receiver_phone;
    /**
     * 物流单号
     */
    private String l_id;
}
