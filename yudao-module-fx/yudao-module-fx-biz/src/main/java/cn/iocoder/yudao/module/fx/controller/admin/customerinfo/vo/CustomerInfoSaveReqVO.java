package cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;

@Schema(description = "管理后台 - 分销商基础信息新增/修改 Request VO")
@Data
public class CustomerInfoSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13099")
    private Long id;

    @Schema(description = "供应商编号", example = "641")
    private Long supplierId;

    @Schema(description = "分销商编号", example = "2509")
    private Long distributorId;

    @Schema(description = "分销商名称", example = "李四")
    private String distributorName;

    @Schema(description = "所属子公司")
    private Long subCompany;

    @Schema(description = "显示名称", example = "赵六")
    private String displayName;

    @Schema(description = "业务归属")
    private String belongTo;

    @Schema(description = "分销商等级")
    private Integer distributorLevel;

    @Schema(description = "是否合作")
    private Integer isCooperate;

    @Schema(description = "是否冻结")
    private Integer isFreeze;

    @Schema(description = "客户渠道属性")
    private String customerChannelDistribute;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "客户类型", example = "2")
    private Integer customerType;

    @Schema(description = "最近下单时间")
    private LocalDateTime latestOrderDate;

    @Schema(description = "是否允许超额提货")
    private Integer isExcessOrder;

    @Schema(description = "分销商地址列表")
    private List<CustomerAddressDO> customerAddressList;

}