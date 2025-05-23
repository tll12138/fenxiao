package cn.iocoder.yudao.module.fx.controller.admin.importorder.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 客商代发 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class ImportOrderExcelVO {

    @ExcelProperty("订单编号")
    private String soId;

    @ExcelProperty("商品编码")
    private String skuId;

    @ExcelProperty("商品数量")
    private BigDecimal productQuantity;

    @ExcelProperty("单价")
    private BigDecimal price;

    @ExcelProperty("省份")
    private String province;

    @ExcelProperty("城市")
    private String city;

    @ExcelProperty("区县")
    private String district;

    @ExcelProperty("详细地址")
    private String address;

    @ExcelProperty("收货人")
    private String contact;

    @ExcelProperty("收货电话")
    private String mobile;

    @ExcelProperty("客商")
    private String customername;

    @ExcelProperty("发货仓")
    private String warehousename;

    @ExcelProperty("快递公司")
    private String expressCompany;

    @ExcelProperty("业务归属")
    private String businessAffiliation;

    @ExcelProperty("收款经销商")
    private String payingDistributor;

    @ExcelProperty("无痕发货")
    private String isTraceless = "否";

    @ExcelProperty("备注")
    private String remark;

}
