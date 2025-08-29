package cn.iocoder.yudao.module.fx.controller.admin.brandauth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 品牌授权 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BrandAuthRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1025")
    @ExcelProperty("id")
    private Integer id;

    @Schema(description = "ip")
    @ExcelProperty("ip")
    private String ip;

    @Schema(description = "唯一标识", example = "9284")
    @ExcelProperty("唯一标识")
    private String uuid;

    @Schema(description = "设备")
    @ExcelProperty("设备")
    private String clientDevice;

    @Schema(description = "操作系统")
    @ExcelProperty("操作系统")
    private String clientOs;

    @Schema(description = "浏览器")
    @ExcelProperty("浏览器")
    private String clientBrowser;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "店铺名称", example = "赵六")
    @ExcelProperty("店铺名称")
    private String storeName;

    @Schema(description = "公司名称/旺旺id", example = "31485")
    @ExcelProperty("公司名称/旺旺id")
    private String storeId;

    @Schema(description = "店铺链接", example = "https://www.iocoder.cn")
    @ExcelProperty("店铺链接")
    private String storeUrl;

    @Schema(description = "授权书接收邮箱")
    @ExcelProperty("授权书接收邮箱")
    private String email;

    @Schema(description = "申请日期")
    @ExcelProperty("申请日期")
    private String applyDate;

    @Schema(description = "申请时间")
    @ExcelProperty("申请时间")
    private String applyDatetime;

    @Schema(description = "是否已阅授权书")
    @ExcelProperty("是否已阅授权书")
    private String isSee;

    @Schema(description = "店铺渠道")
    @ExcelProperty("店铺渠道")
    private BigDecimal channel;

    @Schema(description = "关联流程", example = "32125")
    @ExcelProperty("关联流程")
    private BigDecimal workflowid;

    @Schema(description = "是否线上")
    @ExcelProperty("是否线上")
    private String isOnline;

    @Schema(description = "品牌")
    @ExcelProperty("品牌")
    private String brand;

}