package cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 分销商基础信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerInfoPageReqVO extends PageParam {

    @Schema(description = "供应商编号", example = "641")
    private Long supplierId;

    @Schema(description = "分销商编号", example = "2509")
    private Long distributorId;

    @Schema(description = "分销商名称", example = "李四")
    private String distributorName;

    @Schema(description = "所属子公司")
    private Long subCompany;

    @Schema(description = "显示名称", example = "赵六")
    private String displayName;

    @Schema(description = "业务归属")
    private String belongTo;

    @Schema(description = "分销商等级")
    private Integer distributorLevel;

    @Schema(description = "是否合作")
    private Integer isCooperate;

    @Schema(description = "是否可用")
    private Integer isFreeze;

    @Schema(description = "客户渠道属性")
    private String customerChannelDistribute;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "客户类型", example = "2")
    private Integer customerType;

    @Schema(description = "最近下单时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] latestOrderDate;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "是否允许超额提货")
    private Integer isExcessOrder;

}