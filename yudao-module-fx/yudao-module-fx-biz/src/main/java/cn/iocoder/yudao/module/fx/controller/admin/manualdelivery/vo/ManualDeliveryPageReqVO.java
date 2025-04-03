package cn.iocoder.yudao.module.fx.controller.admin.manualdelivery.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 手动发货信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ManualDeliveryPageReqVO extends PageParam {

    @Schema(description = "快递公司")
    private String expressCompany;

    @Schema(description = "快递公司名称", example = "张三")
    private String expressName;

    @Schema(description = "快递公司编号", example = "6974")
    private String expressId;

    @Schema(description = "快递单号")
    private String express;

    @Schema(description = "销售单号", example = "29215")
    private String soId;

    @Schema(description = "关联销售单", example = "22947")
    private String saleId;

    @Schema(description = "单据状态", example = "1")
    private Integer status;

    @Schema(description = "手动发货原因", example = "不对")
    private String reason;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}