package cn.iocoder.yudao.module.fx.controller.admin.bigcustomeraddress.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 分销大客户地址分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BigCustomerAddressPageReqVO extends PageParam {

    @Schema(description = "分销商", example = "13235")
    private Integer customerId;

    @Schema(description = "分销商名称", example = "13235")
    private String customerName;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "联系人")
    private String person;

    @Schema(description = "联系电话")
    private String contact;

    @Schema(description = "是否可用")
    private String isActive;

    @Schema(description = "审批状态", example = "2")
    private String status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "所属品牌")
    private String brand;

    @Schema(description = "使用次数", example = "21643")
    private BigDecimal count;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}