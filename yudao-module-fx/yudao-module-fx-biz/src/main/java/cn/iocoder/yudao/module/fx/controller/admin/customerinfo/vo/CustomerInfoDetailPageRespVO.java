package cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 分销商基础信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CustomerInfoDetailPageRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13099")
    private Long id;

    @Schema(description = "供应商编号", example = "641")
    private Long supplierId;

    @Schema(description = "分销商编号", example = "2509")
    private Long distributorNum;

    @Schema(description = "分销商名称", example = "李四")
    private String distributorName;

    @Schema(description = "所属子公司")
    private Long subCompany;

    @Schema(description = "显示名称", example = "赵六")
    private String displayName;

    @Schema(description = "业务归属")
    @DictFormat("fx_belong") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String belongTo;

    @Schema(description = "分销商等级")
    @DictFormat("fx_customer_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer distributorLevel;

    @Schema(description = "是否合作")
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer isCooperate;

    @Schema(description = "是否冻结")
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer isFreeze;

    @Schema(description = "是否允许超额提货")
    @DictFormat("yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer isExcessOrder;

    @Schema(description = "客户渠道属性")
    @DictFormat("fx_customer_distribute") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String customerChannelDistribute;

    @Schema(description = "品牌")
    @DictFormat("fx_brand") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String brand;

    @Schema(description = "客户类型", example = "2")
    private Integer customerType;

    @Schema(description = "最近下单时间")
    private LocalDateTime latestOrderDate;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

/*
    @Schema(description = "分销商账号列表")
    private List<CustomerAccountRespVO> customerAccounts;
*/
    @Schema(description = "分销商地址列表")
    private List<CustomerAddressDO> customerAddressList;


}