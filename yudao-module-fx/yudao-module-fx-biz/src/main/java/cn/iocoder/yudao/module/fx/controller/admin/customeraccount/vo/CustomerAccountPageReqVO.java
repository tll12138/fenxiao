package cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 分销商账号分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerAccountPageReqVO extends PageParam {

    @Schema(description = "分销商编号", example = "4297")
    private Long distributorId;

    @Schema(description = "余额")
    private BigDecimal balance;

    @Schema(description = "账户编号", example = "11358")
    private String accountId;

    @Schema(description = "暂扣金额")
    private BigDecimal detainAmount;

    @Schema(description = "是否冻结")
    private Integer isActive;

    @Schema(description = "押金")
    private Integer deposit;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "业务主体", example = "1")
    private Integer company;

    @Schema(description = "货补虚拟金额")
    private BigDecimal vAmount;

    @Schema(description = "是否允许超额提货（0否1是）")
    private Integer isAllow;

    @Schema(description = "超额提货额度")
    private BigDecimal quota;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "是否计算货补（0否1是）")
    private Integer isRep;

    @Schema(description = "暂扣货补金额")
    private BigDecimal zkVAmount;

    @Schema(description = "账户名", example = "张三")
    private String name;

}