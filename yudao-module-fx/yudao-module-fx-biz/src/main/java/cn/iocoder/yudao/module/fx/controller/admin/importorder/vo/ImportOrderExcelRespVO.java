package cn.iocoder.yudao.module.fx.controller.admin.importorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 用户导入 Response VO")
@Data
@Builder
public class ImportOrderExcelRespVO {

    @Schema(description = "创建成功的订单编号数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createSoIds;

    @Schema(description = "更新成功的订单编号数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateSoIds;

    @Schema(description = "导入失败的订单编号集合，key 为订单编号，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureSoIds;

}
