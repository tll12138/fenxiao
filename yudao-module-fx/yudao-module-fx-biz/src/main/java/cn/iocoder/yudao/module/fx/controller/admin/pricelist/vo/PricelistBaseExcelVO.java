package cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class PricelistBaseExcelVO {

    @ExcelProperty("品牌")
    private String brand;

    @ExcelProperty("产品编码")
    private String skuId;

    @ExcelProperty("客户等级")
    private String distributorLevel;

    @ExcelProperty("销售最低价")
    private BigDecimal saleprice;

    @ExcelProperty("是否基础类型")
    private String isNormal;


}