package cn.iocoder.yudao.module.fx.controller.admin.ec2jstorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 分销订单上传中间新增/修改 Request VO")
@Data
public class Ec2jstOrderSaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3668")
    private Integer id;

    @Schema(description = "店铺编码", example = "20430")
    private Integer shopId;

    @Schema(description = "下单时间")
    private LocalDate orderDate;

    @Schema(description = "店铺状态", example = "2")
    private String shopStatus;

    @Schema(description = "买家账号", example = "14601")
    private String shopBuyerId;

    @Schema(description = "省")
    private String receiverState;

    @Schema(description = "市")
    private String receiverCity;

    @Schema(description = "区")
    private String receiverDistrict;

    @Schema(description = "地址")
    private String receiverAddress;

    @Schema(description = "收件人", example = "张三")
    private String receiverName;

    @Schema(description = "联系电话")
    private String receiverPhone;

    @Schema(description = "手机")
    private String receiverMobile;

    @Schema(description = "应付金额")
    private BigDecimal payAmount;

    @Schema(description = "运费")
    private BigDecimal freight;

    @Schema(description = "卖家备注", example = "你猜")
    private String remark;

    @Schema(description = "是否货到付款")
    private String isCod;

    @Schema(description = "修改日期")
    private LocalDateTime shopModified;

    @Schema(description = "快递单号", example = "15590")
    private String lId;

    @Schema(description = "快递公司名称")
    private String logisticsCompany;

    @Schema(description = "订单异常描述")
    private String questionDesc;

    @Schema(description = "卖家标签（旗帜）")
    private String sellerFlag;

    @Schema(description = "快递公司编码", example = "19767")
    private String lcId;

    @Schema(description = "传ERP时间")
    private String toErpTime;

    @Schema(description = "传输标记", example = "1")
    private String erpStatus;

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "聚水潭内部单号", example = "20454")
    private Integer oId;

    @Schema(description = "订单状态 2取消 3取消成功 4取消失败", example = "1")
    private Integer orderStatus;

    @Schema(description = "仓库中文名称（作为聚水潭标签传值）")
    private String warehouse;

    @Schema(description = "SALE销售单,MARKET营销寄样,ZP_ADJUST内部仓正品调整,CP_ADJUST内部仓次品调整")
    private String soFrom;

    @Schema(description = "错误信息")
    private String errorMsg;

    @Schema(description = "调入仓公司内仓库id，主仓=1，销退仓=2，进货仓=3，次品仓=4，自定义1仓=6，自定义2仓=7，自定义3仓=8（对应ERP仓库资料设定页面）")
    private String linkWarehouse;

    @Schema(description = "调出仓公司内仓库id，主仓=1，销退仓=2，进货仓=3，次品仓=4，自定义1仓=6，自定义2仓=7，自定义3仓=8（对应ERP仓库资料设定页面）", example = "9791")
    private String warehouseId;

    @Schema(description = "内部调拨单号ID", example = "17184")
    private String allocateInId;

    @Schema(description = "是否出库 1已出库，0没有，2出库失败")
    private Integer isOut;

    @Schema(description = "失败重试次数")
    private BigDecimal errorNum;

}