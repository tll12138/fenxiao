package cn.iocoder.yudao.module.fx.controller.admin.billapply.vo;

import cn.iocoder.yudao.module.fx.dal.dataobject.billapply.BillApplyDetailDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "管理后台 - 发票申请新增/修改 Request VO")
@Data
public class BillApplySaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2782")
    private Integer id;

    @Schema(description = "申请人")
    private BigDecimal applyMan;

    @Schema(description = "申请日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String applyDate;

    @Schema(description = "开票日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String billDate;

    @Schema(description = "地址及电话")
    private String address;

    @Schema(description = "开户行及账号")
    private String bankNo;

    @Schema(description = "金额合计")
    private BigDecimal amount;

    @Schema(description = "操作员")
    private BigDecimal maker;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "发票类型", example = "2")
    private BigDecimal billType;

    @Schema(description = "金额合计(大写)")
    private String totalAmount;

    @Schema(description = "销售单")
    private String saleOrder;

    @Schema(description = "发票抬头")
    private String billHead;

    @Schema(description = "发票附件")
    private String document;

    @Schema(description = "财务说明")
    private String financialStatement;

    @Schema(description = "购方名称", example = "赵六")
    private String purchaserName;

    @Schema(description = "纳税人识别号")
    private String taxNo;

    @Schema(description = "客户名称", example = "王五")
    private String cusName;

    @Schema(description = "开票信息")
    private String billInfo;

    @Schema(description = "开票流程", example = "10673")
    private String rid;

    @Schema(description = "是否完成", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal isOver;

    @Schema(description = "是否芽肌")
    private BigDecimal isYj;

    @Schema(description = "发票邮箱")
    private String email;

    @Schema(description = "关联邮箱", example = "17061")
    private String emailId;

    @Schema(description = "发票发送状态")
    private BigDecimal isSend;

    @Schema(description = "发票申请详情列表")
    private List<BillApplyDetailDO> billApplyDetails;

}