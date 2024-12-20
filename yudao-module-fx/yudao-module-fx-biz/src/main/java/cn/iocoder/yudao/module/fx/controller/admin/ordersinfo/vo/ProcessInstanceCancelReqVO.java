package cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author zrl
 * @date 2024/11/14
 */
@Data
public class ProcessInstanceCancelReqVO {

    @Schema(description = "流程实例的编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "流程实例的编号不能为空")
    private String id;

    @Schema(description = "取消原因", requiredMode = Schema.RequiredMode.REQUIRED, example = "不请假了！")
    @NotEmpty(message = "取消原因不能为空")
    private String reason;


    @Schema(description = "订单编号")
    private Long orderId;

}
