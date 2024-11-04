package cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.VO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - FX 发货仓库 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SendRepositoryRespVO implements VO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12347")
    @ExcelProperty("主键ID")
    private Integer id;

    @Schema(description = "仓库名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("仓库名称")
    private String name;

    @Schema(description = "仓库类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "仓库类型", converter = DictConvert.class)
    @DictFormat("fx_repository_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer type;

    @Schema(description = "仓库编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123")
    @ExcelProperty("仓库编码")
    private String code;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "仓库全称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("仓库全称")
    private String allName;

    @Schema(description = "是否传erp")
    @ExcelProperty("是否传erp")
    private Integer isToErp;

    @Trans(type = TransType.SIMPLE, target = AdminUserDO.class,fields = "nickname", ref = "creator")
    private String creator;

    @Trans(type = TransType.SIMPLE, target = AdminUserDO.class,fields = "nickname", ref = "updater")
    private String updater;

}