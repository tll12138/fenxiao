package cn.iocoder.yudao.module.fx.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * Bpm 错误码枚举类
 * <p>
 * fx 系统，使用 1_010_000_000 段
 */
public interface ErrorCodeConstants {
    // ========== 系统 =================
    ErrorCode SYSTEM_ERROR = new ErrorCode(1_010_000_000, "系统异常");

    // ========== 分销商基础信息
    ErrorCode CUSTOMER_INFO_NOT_EXISTS = new ErrorCode(1_010_000_100, "分销商基础信息不存在");

    // ========== 分销商地址
    ErrorCode CUSTOMER_ADDRESS_NOT_EXISTS = new ErrorCode(1_010_000_200, "分销商地址不存在");

    // ========== 分销商账号
    ErrorCode CUSTOMER_ACCOUNT_NOT_EXISTS = new ErrorCode(1_010_000_300, "分销商账号不存在");
    ErrorCode CUSTOMER_ACCOUNT_CREATE_FAIL = new ErrorCode(1_010_000_301, "分销商账号创建失败");
    ErrorCode CUSTOMER_ADDRESS_UPDATE_FAIL = new ErrorCode(1_010_000_302, "分销商地址更新失败");


    // ========== 子公司信息
    ErrorCode SUB_COMPANY_INFO_NOT_EXISTS = new ErrorCode(1_010_000_401, "子公司信息不存在");


    // ========== 销售单

    ErrorCode ORDERS_INFO_PARAMS_ERROR = new ErrorCode(1_020_000_007, "订单参数为空");
    ErrorCode ORDERS_INFO_NOT_EXISTS = new ErrorCode(1_020_000_001, "销售单不存在");
    ErrorCode ORDERS_DETAIL_NOT_EXISTS = new ErrorCode(1_020_000_002, "分销-销售订单明细不存在");
    ErrorCode ORDERS_DETAIL_BRAND_NOT_SAME = new ErrorCode(1_020_000_003, "商品必须为同一品牌的商品！");

    ErrorCode ORDERS_GOODS_INVENTORY_NOT_ENOUGH = new ErrorCode(1_020_000_004,
            "商品skuId：[%s]，超出库存数量，订单中该商品数：%s，总可用库存数: %s");
    ErrorCode ORDERS_ACCOUNT_BALANCES_NOT_ENOUGH = new ErrorCode(1_020_000_005,
            "收货方账户余额不足， 账号余额：%s");
    ErrorCode ORDER_INFO_BRAND_NOT_SAME = new ErrorCode(1_020_000_006, "销售单商品包含不同品牌");


    //=====================================  发货仓库  =================================
    ErrorCode SEND_REPOSITORY_NOT_EXISTS = new ErrorCode(1_020_200_001, "分销-发货仓库不存在");


    // 钉钉通知
    ErrorCode DING_TODO_TALK_UPDATE_FAIL = new ErrorCode(1_020_300_001, "钉钉代办状态修改失败");

    // =====================================  销售退货单  =================================
    ErrorCode RETURN_ORDER_NOT_EXISTS = new ErrorCode(1_020_400_001, "FX 销售退货单不存在");

    ErrorCode RETURN_ORDER_DETAIL_QUANTITY_ILLEGAL_1 = new ErrorCode(1_020_400_002, "退货数量不能大于原单数量");

    ErrorCode RETURN_ORDER_DETAIL_QUANTITY_ILLEGAL_2 = new ErrorCode(1_020_400_002, "退货数量不能小于0");
}
