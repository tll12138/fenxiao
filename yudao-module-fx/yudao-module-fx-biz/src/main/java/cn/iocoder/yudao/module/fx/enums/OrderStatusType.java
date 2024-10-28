package cn.iocoder.yudao.module.fx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zrl
 * @date 2024/10/28
 */
@Getter
@AllArgsConstructor
public enum OrderStatusType {
    /**
     * 未提交	0
     * 审核中	1
     * 已发货	2
     * 外部发货中	3
     * 已取消	4
     * 已审核待传ERP	5
     * 流程创建失败	6
     * 已入库	7
     * ERP收货中	8
     * 财务审核中	9
     * 商品审核中	10
     * 已完成	11
     * 已提交待审核	12
     * 账户缺钱中	13
     */
    UN_SUBMITTED(0, "未提交"),

    AUDITING(1, "审核中"),

    SHIPPED(2, "已发货"),

    EXTERNAL_SHIPPING(3, "外部发货中"),

    CANCELLED(4, "已取消"),

    WAITING_FOR_ERP(5, "已审核待传ERP"),

    CREATION_FAILED(6, "流程创建失败"),

    IN_WAREHOUSE(7, "已入库"),

    ERP_RECEIVING(8, "ERP收货中"),

    FINANCE_AUDITING(9, "财务审核中"),

    GOODS_AUDITING(10, "商品审核中"),

    COMPLETED(11, "已完成"),

    SUBMITTED(12, "已提交待审核"),

    ACCOUNT_NO_MONEY(13, "账户缺钱中")
    ;

    private final Integer type;
    private final String value;
}
