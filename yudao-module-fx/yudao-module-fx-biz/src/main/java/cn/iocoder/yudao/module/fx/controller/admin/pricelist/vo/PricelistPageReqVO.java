package cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 分销价格对照分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PricelistPageReqVO extends PageParam {

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
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "品牌id")
    private String brandId;

}