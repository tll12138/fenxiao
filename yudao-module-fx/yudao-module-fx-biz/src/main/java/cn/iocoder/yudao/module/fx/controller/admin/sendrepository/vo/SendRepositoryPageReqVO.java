package cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - FX 发货仓库分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SendRepositoryPageReqVO extends PageParam {

    @Schema(description = "仓库名称", example = "王五")
    private String name;

    @Schema(description = "仓库类型", example = "1")
    private Integer type;

    @Schema(description = "仓库编码", example = "123")
    private String code;

    @Schema(description = "备注", example = "备注")
    private String remark;

    @Schema(description = "仓库全称", example = "李四")
    private String allName;

    @Schema(description = "是否传erp")
    private Integer isToErp;

    @Schema(description = "主仓公司编号")
    private String coId;

    @Schema(description = "是否可用")
    private Integer isUsed;

    @Schema(description = "是否内部仓")
    private Integer isInside;

    @Schema(description = "渠道")
    private Integer channel;

}