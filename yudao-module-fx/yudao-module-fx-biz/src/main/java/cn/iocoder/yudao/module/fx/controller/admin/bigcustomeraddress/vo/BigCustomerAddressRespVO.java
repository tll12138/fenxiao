package cn.iocoder.yudao.module.fx.controller.admin.bigcustomeraddress.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 分销大客户地址 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BigCustomerAddressRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12771")
    @ExcelProperty("序号")
    private Long id;

    @Schema(description = "分销商", example = "13235")
    @ExcelProperty("分销商")
    private Integer customerId;

    @Schema(description = "分销商名称", example = "13235")
    @ExcelProperty("分销商名称")
    private String customerName;

    @Schema(description = "省")
    @ExcelProperty("省")
    private String province;

    @Schema(description = "市")
    @ExcelProperty("市")
    private String city;

    @Schema(description = "区")
    @ExcelProperty("区")
    private String district;

    @Schema(description = "地址")
    @ExcelProperty("地址")
    private String address;

    @Schema(description = "联系人")
    @ExcelProperty("联系人")
    private String person;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String contact;

    @Schema(description = "是否可用")
    @ExcelProperty(value = "是否可用", converter = DictConvert.class)
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String isActive;

    @Schema(description = "审批状态", example = "2")
    @ExcelProperty("审批状态")
    private String status;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "所属品牌")
    @ExcelProperty(value = "所属品牌", converter = DictConvert.class)
    @DictFormat("fx_brand") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String brand;

    @Schema(description = "使用次数", example = "21643")
    @ExcelProperty("使用次数")
    private BigDecimal count;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}