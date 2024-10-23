package cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 分销商基础信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CustomerInfoRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13099")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "供应商编号", example = "641")
    @ExcelProperty("供应商编号")
    private Long supplierId;

    @Schema(description = "分销商编号", example = "2509")
    @ExcelProperty("分销商编号")
    private Long distributorId;

    @Schema(description = "分销商名称", example = "李四")
    @ExcelProperty("分销商名称")
    private String distributorName;

    @Schema(description = "所属子公司")
    @ExcelProperty("所属子公司")
    private Long subCompany;

    @Schema(description = "显示名称", example = "赵六")
    @ExcelProperty("显示名称")
    private String displayName;

    @Schema(description = "业务归属")
    @ExcelProperty(value = "业务归属", converter = DictConvert.class)
    @DictFormat("fx_belong") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String belongTo;

    @Schema(description = "分销商等级")
    @ExcelProperty(value = "分销商等级", converter = DictConvert.class)
    @DictFormat("fx_customer_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer distributorLevel;

    @Schema(description = "是否合作")
    @ExcelProperty(value = "是否合作", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer isCooperate;

    @Schema(description = "是否冻结")
    @ExcelProperty(value = "是否冻结", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer isFreeze;

    @Schema(description = "是否允许超额提货")
    @ExcelProperty(value = "是否允许超额提货", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer isExcessOrder;

    @Schema(description = "客户渠道属性")
    @ExcelProperty(value = "客户渠道属性", converter = DictConvert.class)
    @DictFormat("fx_customer_distribute") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String customerChannelDistribute;

    @Schema(description = "品牌")
    @ExcelProperty(value = "品牌", converter = DictConvert.class)
    @DictFormat("fx_brand") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String brand;

    @Schema(description = "客户类型", example = "2")
    @ExcelProperty("客户类型")
    private Integer customerType;

    @Schema(description = "最近下单时间")
    @ExcelProperty("最近下单时间")
    private LocalDateTime latestOrderDate;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;



}