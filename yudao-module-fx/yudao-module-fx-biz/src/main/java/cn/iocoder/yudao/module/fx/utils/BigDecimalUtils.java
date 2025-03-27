package cn.iocoder.yudao.module.fx.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal 工具类
 */
public final class BigDecimalUtils {

    private BigDecimalUtils() {
        // 私有构造函数防止实例化
    }

    // 默认运算精度（保留2位小数）
    private static final int DEFAULT_SCALE = 2;

    /**
     * 加法运算（默认四舍五入保留2位小数）
     */
    public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
        b1 = nullToZero(b1);
        b2 = nullToZero(b2);
        return b1.add(b2).setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 减法运算（默认四舍五入保留2位小数）
     */
    public static BigDecimal subtract(BigDecimal b1, BigDecimal b2) {
        b1 = nullToZero(b1);
        b2 = nullToZero(b2);
        return b1.subtract(b2).setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 乘法运算（默认四舍五入保留2位小数）
     */
    public static BigDecimal multiply(BigDecimal b1, BigDecimal b2) {
        b1 = nullToZero(b1);
        b2 = nullToZero(b2);
        return b1.multiply(b2).setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 除法运算（默认四舍五入保留2位小数）
     */
    public static BigDecimal divide(BigDecimal b1, BigDecimal b2) {
        b1 = nullToZero(b1);
        b2 = nullToZero(b2);
        if (b2.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return b1.divide(b2, DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 等于比较
     */
    public static boolean eq(BigDecimal b1, BigDecimal b2) {
        return compare(b1, b2) == 0;
    }

    /**
     * b1大于b2比较
     */
    public static boolean gt(BigDecimal b1, BigDecimal b2) {
        return compare(b1, b2) > 0;
    }

    /**
     * 大于等于比较
     */
    public static boolean ge(BigDecimal b1, BigDecimal b2) {
        return compare(b1, b2) >= 0;
    }

    /**
     * b1小于b2比较
     */
    public static boolean lt(BigDecimal b1, BigDecimal b2) {
        return compare(b1, b2) < 0;
    }

    /**
     * 小于等于比较
     */
    public static boolean le(BigDecimal b1, BigDecimal b2) {
        return compare(b1, b2) <= 0;
    }

    /**
     * 安全比较（处理null值）
     */
    private static int compare(BigDecimal b1, BigDecimal b2) {
        return nullToZero(b1).compareTo(nullToZero(b2));
    }

    /**
     * 将null转换为BigDecimal.ZERO
     */
    private static BigDecimal nullToZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}