package cn.iocoder.yudao.module.fx.controller.admin.ec2jstorderitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 分销订单上传详情中间新增/修改 Request VO")
@Data
public class Ec2jstOrderitemSaveReqVO {

    @Schema(description = "商品编码", example = "7279")
    private String skuiId;

    @Schema(description = "店铺商品编码", example = "9307")
    private String shopSkuId;

    @Schema(description = "erp款号", example = "18296")
    private String iId;

    @Schema(description = "图片地址")
    private String pic;

    @Schema(description = "商品属性")
    private String propertiesValue;

    @Schema(description = "成交总额")
    private BigDecimal amount;

    @Schema(description = "原价", example = "9286")
    private BigDecimal basePrice;

    @Schema(description = "数量")
    private BigDecimal qty;

    @Schema(description = "商品名称", example = "张三")
    private String name;

    @Schema(description = "状态", example = "2")
    private String refundStatus;

    @Schema(description = "出库oid", example = "13620")
    private String outerOiId;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "订单表id", example = "7293")
    private BigDecimal mainid;

}