package cn.iocoder.yudao.module.fx.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * Bpm 错误码枚举类
 * <p>
 * fx 系统，使用 1_010_000_000 段
 */
public interface ErrorCodeConstants {

    // ========== 分销商基础信息
    ErrorCode CUSTOMER_INFO_NOT_EXISTS = new ErrorCode(1_010_000_100, "分销商基础信息不存在");

    // ========== 分销商地址
    ErrorCode CUSTOMER_ADDRESS_NOT_EXISTS = new ErrorCode(1_010_000_200, "分销商地址不存在");

    // ========== 分销商账号
    ErrorCode CUSTOMER_ACCOUNT_NOT_EXISTS = new ErrorCode(1_010_000_300, "分销商账号不存在");
    ErrorCode CUSTOMER_ACCOUNT_CREATE_FAIL = new ErrorCode(1_010_000_301, "分销商账号创建失败");
    ErrorCode CUSTOMER_ADDRESS_UPDATE_FAIL = new ErrorCode(1_010_000_301, "分销商地址更新失败");


    // ========== 子公司信息
    ErrorCode SUB_COMPANY_INFO_NOT_EXISTS = new ErrorCode(1_010_000_401, "子公司信息不存在");


    // ========== 销售单
    ErrorCode ORDERS_INFO_NOT_EXISTS = new ErrorCode(1_020_000_001, "销售单不存在");
    ErrorCode ORDERS_DETAIL_NOT_EXISTS = new ErrorCode(1_020_100_001, "分销-销售订单明细不存在");

    // ========== 发货仓库
    ErrorCode SEND_REPOSITORY_NOT_EXISTS = new ErrorCode(1_020_200_001, "分销-发货仓库不存在");
}
