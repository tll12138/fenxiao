package cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.VO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - FX 销售退货单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReturnOrderRespVO extends BaseDO implements VO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "单据编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "877")
    @ExcelProperty("单据编号")
    private String orderId;

    @Schema(description = "单据日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("单据日期")
    private LocalDate orderDate;

    @Schema(description = "单据状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "单据状态", converter = DictConvert.class)
    @DictFormat("fx_order_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer orderStatus;

    @Schema(description = "退货类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "退货类型", converter = DictConvert.class)
    @DictFormat("fx_return_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer returnType;

    @Schema(description = "原销售单", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("原销售单")
    private String originOrder;

    @Schema(description = "退货方", requiredMode = Schema.RequiredMode.REQUIRED, example = "28259")
    @ExcelProperty("退货方")
    @Trans(type = TransType.SIMPLE, target = CustomerInfoDO.class, fields = "displayName", ref = "returnUserName")
    private Long returnUserId;

    @Schema(description = "退货方名称")
    private String returnUserName;

    @Schema(description = "物流单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("物流单号")
    private String logisticsNumber;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "总退货金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("总退货金额")
    private BigDecimal totalReturnAmount;

    @Schema(description = "总退货数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("总退货数量")
    private Integer totalReturnQuantity;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "品牌", requiredMode = Schema.RequiredMode.REQUIRED)
    private String brand;

}