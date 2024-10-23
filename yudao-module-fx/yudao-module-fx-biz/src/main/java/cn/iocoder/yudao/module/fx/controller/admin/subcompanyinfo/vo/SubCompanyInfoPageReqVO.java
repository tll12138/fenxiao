package cn.iocoder.yudao.module.fx.controller.admin.subcompanyinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 子公司信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubCompanyInfoPageReqVO extends PageParam {

    @Schema(description = "公司名称", example = "张三")
    private String companyName;

    @Schema(description = "识别人编号", example = "2418")
    private String identifyId;

    @Schema(description = "开户行")
    private String bank;

    @Schema(description = "地区")
    private String region;

}