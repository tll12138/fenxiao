package cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 分销价格对照新增/修改 Request VO")
@Data
public class PricelistSaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14700")
    private Integer id;

    @Schema(description = "客户")
    private String customer;

    @Schema(description = "产品编码", example = "14456")
    private String skuId;

    @Schema(description = "规格")
    private String category;

    @Schema(description = "销售最低价", example = "12214")
    private BigDecimal saleprice;

    @Schema(description = "客户等级")
    @DictFormat("fx_customer_level")
    private Integer distributorLevel;

    @Schema(description = "产品名称", example = "张三")
    private String name;

    @Schema(description = "是否基础类型")
    private String isNormal;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "客户id")
    private Integer customerId;

    @Schema(description = "品牌id")
    private String brandId;

}