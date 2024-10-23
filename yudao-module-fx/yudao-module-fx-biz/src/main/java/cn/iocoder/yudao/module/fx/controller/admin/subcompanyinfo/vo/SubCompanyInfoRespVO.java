package cn.iocoder.yudao.module.fx.controller.admin.subcompanyinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 子公司信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SubCompanyInfoRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12000")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "公司名称", example = "张三")
    @ExcelProperty("公司名称")
    private String companyName;

    @Schema(description = "识别人编号", example = "2418")
    @ExcelProperty("识别人编号")
    private String identifyId;

    @Schema(description = "开户行")
    @ExcelProperty("开户行")
    private String bank;

    @Schema(description = "地区")
    @ExcelProperty("地区")
    private String region;

}