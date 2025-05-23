package cn.iocoder.yudao.module.fx.controller.admin.skucostprice.vo;

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
public class SkuCostPriceExcelVO {

    @ExcelProperty("品牌")
    private String brand;

    @ExcelProperty("商品编码")
    private String skuId;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("规格")
    private String value;

    @ExcelProperty("品类")
    private String type;

    @ExcelProperty("属性")
    private String paid;

    @ExcelProperty("财务结算价")
    private BigDecimal costPrice;

    @ExcelProperty("采购成本")
    private BigDecimal costOtherprice;

    @ExcelProperty("出库成本")
    private BigDecimal outCost;


}