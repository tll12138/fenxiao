package cn.iocoder.yudao.module.fx.controller.admin.subcompanyinfo.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;

@Schema(description = "管理后台 - 子公司信息新增/修改 Request VO")
@Data
public class SubCompanyInfoSaveReqVO {

    @Schema(description = "ID", example = "123")
    private Long id;

    @Schema(description = "公司名称", example = "张三")
    private String companyName;

    @Schema(description = "识别人编号", example = "2418")
    private String identifyId;

    @Schema(description = "开户行")
    private String bank;

    @Schema(description = "地区")
    private String region;

}