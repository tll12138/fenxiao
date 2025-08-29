package cn.iocoder.yudao.module.fx.controller.admin.brandauth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 品牌授权新增/修改 Request VO")
@Data
public class BrandAuthSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1025")
    private Integer id;

    @Schema(description = "ip")
    private String ip;

    @Schema(description = "唯一标识", example = "9284")
    private String uuid;

    @Schema(description = "设备")
    private String clientDevice;

    @Schema(description = "操作系统")
    private String clientOs;

    @Schema(description = "浏览器")
    private String clientBrowser;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "店铺名称", example = "赵六")
    private String storeName;

    @Schema(description = "公司名称/旺旺id", example = "31485")
    private String storeId;

    @Schema(description = "店铺链接", example = "https://www.iocoder.cn")
    private String storeUrl;

    @Schema(description = "授权书接收邮箱")
    private String email;

    @Schema(description = "申请日期")
    private String applyDate;

    @Schema(description = "申请时间")
    private String applyDatetime;

    @Schema(description = "是否已阅授权书")
    private String isSee;

    @Schema(description = "店铺渠道")
    private BigDecimal channel;

    @Schema(description = "关联流程", example = "32125")
    private BigDecimal workflowid;

    @Schema(description = "是否线上")
    private String isOnline;

    @Schema(description = "品牌")
    private String brand;

}