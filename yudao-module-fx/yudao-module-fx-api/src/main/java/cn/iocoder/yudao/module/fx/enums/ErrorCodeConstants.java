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
    ErrorCode CUSTOMER_ACCOUNT_PARAMS_EMPTY = new ErrorCode(1_010_000_303, "获取分销商账号参数为空");
    ErrorCode CUSTOMER_INFO_SYNC_EMPTY = new ErrorCode(1_010_000_304, "oa同步分销商信息参数为空");
    ErrorCode CUSTOMER_INFO_ID_NULL = new ErrorCode(1_010_000_305, "分销商id不能为空");
    ErrorCode CUSTOMER_INFO_SYNC_ERROR = new ErrorCode(1_010_000_306, "oa同步分销商信息失败");
    ErrorCode CUSTOMER_INFO_DATE_PARSE_ERROR = new ErrorCode(1_010_000_307, "日期格式解析失败");


    // ========== 子公司信息
    ErrorCode SUB_COMPANY_INFO_NOT_EXISTS = new ErrorCode(1_010_000_401, "子公司信息不存在");


    // ========== 销售单

    ErrorCode ORDERS_INFO_PARAMS_ERROR = new ErrorCode(1_020_000_007, "订单参数为空");
    ErrorCode ORDERS_INFO_NOT_EXISTS = new ErrorCode(1_020_000_001, "销售单不存在");
    ErrorCode RETURN_ORDERS_INFO_NOT_EXISTS = new ErrorCode(1_020_000_001, "退货单不存在");
    ErrorCode ORDERS_DETAIL_NOT_EXISTS = new ErrorCode(1_020_000_002, "分销-销售订单明细不存在");
    ErrorCode ORDERS_DETAIL_BRAND_NOT_SAME = new ErrorCode(1_020_000_003, "商品必须为同一品牌的商品！");

    ErrorCode ORDERS_GOODS_INVENTORY_NOT_ENOUGH = new ErrorCode(1_020_000_004,
            "商品skuId：[%s]，超出库存数量，订单中该商品数：%s，总可用库存数: %s");
    ErrorCode ORDERS_ACCOUNT_BALANCES_NOT_ENOUGH = new ErrorCode(1_020_000_005,
            "收货方账户余额不足且不允许超额， 账号余额：%s");
    ErrorCode ORDER_INFO_BRAND_NOT_SAME = new ErrorCode(1_020_000_006, "销售单商品包含不同品牌");
    ErrorCode ORDERS_ACCOUNT_DETAIN_BALANCES_NOT_ENOUGH = new ErrorCode(1_020_000_007,
            "收货方账户暂扣金额小于销售单金额");
    ErrorCode ORDERS_SYNC_ERROR = new ErrorCode(1_020_000_008,
            "订单同步失败");
    ErrorCode ORDERS_SYNC_ORDER_EMPTY = new ErrorCode(1_020_000_009,
            "订单同步失败，订单列表为空");
    ErrorCode ORDERS_SYNC_ORDER_DETAIL_EMPTY = new ErrorCode(1_020_000_010,
            "订单同步失败，订单明细列表为空");
    ErrorCode ORDERS_SYNC_ORDER_DETAIL_CORRESPOND_EMPTY = new ErrorCode(1_020_000_011,
            "订单同步失败，订单明细列表为空，销售单单据号：%s");


    //=====================================  发货仓库  =================================
    ErrorCode SEND_REPOSITORY_NOT_EXISTS = new ErrorCode(1_020_200_001, "分销-发货仓库不存在");


    // 钉钉通知
    ErrorCode DING_TODO_TALK_UPDATE_FAIL = new ErrorCode(1_020_300_001, "钉钉代办状态修改失败");

    // =====================================  销售退货单  =================================
    ErrorCode RETURN_ORDER_NOT_EXISTS = new ErrorCode(1_020_400_001, "FX 销售退货单不存在");

    ErrorCode RETURN_ORDER_DETAIL_QUANTITY_ILLEGAL_1 = new ErrorCode(1_020_400_002, "退货数量不能大于原单数量");

    ErrorCode RETURN_ORDER_DETAIL_QUANTITY_ILLEGAL_2 = new ErrorCode(1_020_400_002, "退货数量不能小于0");

    // =====================================  商品信息  =================================
    ErrorCode GOODS_ARCHIVES_NOT_EXISTS = new ErrorCode(1_110_000_100, "商品信息不存在");

    // =====================================  商品库存信息  =================================
    ErrorCode INVENTORY_DATA_NOT_EXISTS = new ErrorCode(1_120_000_100, "商品库存信息不存在");
    // ========== 分销订单上传中间 ==========
    ErrorCode EC2JST_ORDER_NOT_EXISTS = new ErrorCode(1_130_000_100, "分销订单上传中间不存在");
    // ========== 分销订单上传详情中间  ==========
    ErrorCode EC2JST_ORDERITEM_NOT_EXISTS = new ErrorCode(1_140_000_100, "分销订单上传详情中间不存在");
    // ========== 分销价格对照
    ErrorCode PRICELIST_NOT_EXISTS = new ErrorCode(1_150_000_100, "分销价格对照不存在");
    ErrorCode IMPORT_PRICE_LIST_IS_EMPTY = new ErrorCode(1_150_000_101, "导入分销价格对照数据不能为空！");
    ErrorCode PRICE_LIST_EXISTS = new ErrorCode(1_150_000_102, "导入数据已经存在了");
    ErrorCode IMPORT_PRICE_BASE_LIST_IS_EMPTY = new ErrorCode(1_150_000_103, "导入分销商品基础数据不能为空！");
    // ========== 发票申请  ==========
    ErrorCode BILL_APPLY_NOT_EXISTS = new ErrorCode(1_160_000_100, "发票申请不存在");
    // ========== 发票邮箱库 ==========
    ErrorCode EMAIL_ADDRESS_NOT_EXISTS = new ErrorCode(1_170_000_100, "发票邮箱库不存在");
    // ==========  分销打款账户 ==========
    ErrorCode FROM_ACCOUNT_NOT_EXISTS = new ErrorCode(1_180_000_100, " 分销打款账户不存在");
    // ========== 分销账户调整  ==========
    ErrorCode ACCOUNT_ADJUST_NOT_EXISTS = new ErrorCode(1_190_000_100, "分销账户调整不存在");
    // ========== 分销账户月结  ==========
    ErrorCode MON_SETTLEMENT_NOT_EXISTS = new ErrorCode(1_200_000_100, "分销账户月结不存在");
    // ========== 分销账户收款记录  ==========
    ErrorCode ACCOUNT_COLLECTION_NOT_EXISTS = new ErrorCode(1_201_000_100, "分销账户收款记录不存在");
    // ========== 分销账户资金调整记录  ==========
    ErrorCode AMOUNT_ADJ_NOT_EXISTS = new ErrorCode(1_202_000_100, "分销账户资金调整记录不存在");
    // ========== 分销支付账户  ==========
    ErrorCode PAY_ACCOUNT_NOT_EXISTS = new ErrorCode(1_203_000_100, "分销支付账户不存在");
    // ========== 客商账户初始化配置  ==========
    ErrorCode ACC_INFO_CONFIG_NOT_EXISTS = new ErrorCode(1_204_000_100, "客商账户初始化配置不存在");
    // ========== 客商代发单  ==========
    ErrorCode IMPORT_ORDER_NOT_EXISTS = new ErrorCode(1_205_000_100, "客商代发单不存在");
    ErrorCode IMPORT_ORDER_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_205_000_400, "导入客商代发数据不能为空！");
    ErrorCode IMPORT_ORDER_EXISTS = new ErrorCode(1_205_000_201, "客商代发单已经存在");
    ErrorCode IMPORT_ORDER_GENERATED_SALE = new ErrorCode(1_205_000_202, "该订单已生成销售单，不允许修改");
    ErrorCode IMPORT_ORDER_ERROR = new ErrorCode(1_205_000_404, "客商代发单导入失败");
    // ========== 分销大客户地址 ==========
    ErrorCode BIG_CUSTOMER_ADDRESS_NOT_EXISTS = new ErrorCode(1_206_000_100, "分销大客户地址不存在");
    // ========== 商品箱规  ==========
    ErrorCode SK_BOXSIZE_NOT_EXISTS = new ErrorCode(1_207_000_100, "商品箱规不存在");
    // ========== 商品成本 ==========
    ErrorCode SKU_COSTPRICE_NOT_EXISTS = new ErrorCode(1_208_000_100, "商品成本不存在");
    ErrorCode IMPORT_SKU_COST_PRICE_IS_EMPTY = new ErrorCode(1_208_000_101, "导入商品成本数据不能为空！");
    // ========== 手动发货信息  ==========
    ErrorCode MANUAL_DELIVERY_NOT_EXISTS = new ErrorCode(1_209_000_100, "手动发货信息不存在");
    ErrorCode MANUAL_DELIVERY_JUSHUITAN_ERROR = new ErrorCode(1_209_000_101, "发货失败，聚水潭返回错误{}");
    ErrorCode MANUAL_DELIVERY_SO_ID_NOT_EXISTS = new ErrorCode(1_209_000_102, "发货失败，销售单{}不存在");
    ErrorCode MANUAL_DELIVERY_ERROR = new ErrorCode(1_209_000_103, "发货失败，{}");
    ErrorCode MANUAL_DELIVERY_ERP_NO_NOT_EXIST = new ErrorCode(1_209_000_104, "手动发货失败，erp单号为空");
    // ========== 聚水潭发货回传中间表 ==========
    ErrorCode JST_ORDER_OUT_NOT_EXISTS = new ErrorCode(1_301_000_100, "聚水潭发货回传中间表不存在");
    ErrorCode JST_ORDER_OUT_ERROR_EXPRESS_NOT_EXIST = new ErrorCode(1_301_000_101, "聚水潭发货回传执行失败，订单{}未找到匹配的物流公司编码，维护物流公司编码后重试！");
    ErrorCode JST_ORDER_OUT_ERROR_SALE_FORM_EMPTY = new ErrorCode(1_301_000_102, "聚水潭发货回传执行失败，订单{}未找到匹配的单据来源，请确认！");
    // ========== 销售退货详情  ==========
    ErrorCode RETURN_ORDER_DETAIL_NOT_EXISTS = new ErrorCode(1_302_000_100, "销售退货详情不存在");
    // ========== 客商账户收款审核 ==========
    ErrorCode CA_RCPT_AUD_NOT_EXISTS = new ErrorCode(1_303_000_100, "客商账户收款审核不存在");
    ErrorCode CA_RCPT_AUD_ID_NOT_EXISTS = new ErrorCode(1_303_000_101, "客商账户收款审核id不存在");
    // ========== 开票信息 ==========
    ErrorCode BILLING_INFO_NOT_EXISTS = new ErrorCode(1_304_000_100, "开票信息不存在");
    // ========== 品牌授权  ==========
    ErrorCode BRAND_AUTH_NOT_EXISTS = new ErrorCode(1_305_000_100, "品牌授权不存在");
}
