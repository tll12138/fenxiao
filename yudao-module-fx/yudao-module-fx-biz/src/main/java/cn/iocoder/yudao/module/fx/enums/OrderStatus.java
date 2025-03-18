package cn.iocoder.yudao.module.fx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tll
 * @date 2025-03-04 11:09:10
 */
@Getter
@AllArgsConstructor
public enum OrderStatus {

    NOT_SUBMITTED(0, "未提交"),
    REVIEW(1, "审核中"),
    SHIPPED(2, "已发货"),
    EXTERNAL_SHIPMENT(3, "外部发货中"),
    CANCELLED(4, "已取消"),
    PENDING_TRANSFER_ERP(5, "已审核待传ERP"),
    PROCESS_CREATION_FAILED(5, "流程创建失败"),
    IN_STOCK(5, "已入库"),
    RECEIVING_GOODS(5, "ERP收货中"),
    FINANCIAL_REVIEW(5, "财务审核中"),
    PRODUCT_REVIEW(5, "商品审核中"),
    COMPLETED(5, "已完成"),
    PENDING_APPROVAL(5, "已提交待审核"),
    SHORT_MONEY(5, "账户缺钱中"),
    ;

    private final Integer code;
    private final String value;
}
