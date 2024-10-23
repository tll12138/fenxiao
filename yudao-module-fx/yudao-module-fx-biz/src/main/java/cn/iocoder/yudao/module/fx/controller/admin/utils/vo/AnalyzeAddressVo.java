package cn.iocoder.yudao.module.fx.controller.admin.utils.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author zrl
 * @date 2024/7/26
 */

@Schema(description = "管理后台 - 地址解析返回VO")
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AnalyzeAddressVo {

    @Schema(description = "姓名")
    private String person;

    @Schema(description = "手机号")
    private String phonenum;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "省份编码")
    private String provinceCode;

    @Schema(description = "详细地址")
    private String text;

    @Schema(description = "区县")
    private String county;

    @Schema(description = "区县编码")
    private String countyCode;

    @Schema(description = "城市编码")
    private String county_code;

    @Schema(description = "镇")
    private String town;

    @Schema(description = "镇编码")
    private String townCode;

    @Schema(description = "详细地址")
    private String detail;
}
