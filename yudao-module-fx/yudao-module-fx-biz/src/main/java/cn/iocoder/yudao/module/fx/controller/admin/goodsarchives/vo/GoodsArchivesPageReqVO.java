package cn.iocoder.yudao.module.fx.controller.admin.goodsarchives.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 分销商品资料分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GoodsArchivesPageReqVO extends PageParam {

    @Schema(description = "商品编码", example = "23688")
    private String skuId;

    @Schema(description = "商品名称")
    private String name;

    @Schema(description = "商品简称")
    private String shortName;

    @Schema(description = "标准价")
    private BigDecimal salePrice;

    @Schema(description = "成本价")
    private BigDecimal costPrice;

    @Schema(description = "规格")
    private String propertiesValue;

    @Schema(description = "分类")
    private String category;

    @Schema(description = "虚拟分类", example = "芋艿")
    private String vcName;

    @Schema(description = "商品属性", example = "2")
    private String itemType;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "商品类型")
    private String skuType;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "开票名称")
    private String billingName;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "商品图片")
    private String picBig;

    @Schema(description = "款式编码")
    private String iId;

    @Schema(description = "市场价")
    private BigDecimal marketPrice;

    @Schema(description = "是否启用")
    private String enabled;

    @Schema(description = "是否分销商品")
    private String isFx;

    @Schema(description = "实际成本价")
    private BigDecimal actualCostPrice;

    @Schema(description = "销售成本价")
    private BigDecimal saleCostPrice;

    @Schema(description = "一级分类")
    private String level1Category;

    @Schema(description = "二级分类")
    private String level2Category;

    @Schema(description = "是否参与计算")
    private String isCount;

    @Schema(description = "是否正装")
    private String isFormal;

    @Schema(description = "净重(kg)")
    private BigDecimal weight;

    @Schema(description = "国标码")
    private String scancode;

    @Schema(description = "是否组合商品")
    private String isGroup;

    @Schema(description = "仓库id")
    @NotBlank(message = "发货仓库不能为空")
    private String warehouseCode;

}