package cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - FX 发货仓库分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SendRepositoryPageReqVO extends PageParam {

    @Schema(description = "仓库名称", example = "王五")
    private String name;

    @Schema(description = "仓库类型", example = "1")
    private Integer type;

    @Schema(description = "仓库全称", example = "李四")
    private String allName;

    @Schema(description = "是否传erp")
    private Integer isToErp;

}