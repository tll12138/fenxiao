package cn.iocoder.yudao.module.fx.controller.admin.returnorder;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.ProcessInstanceCancelReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrderPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrderRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrderSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorder.ReturnOrderDO;
import cn.iocoder.yudao.module.fx.service.returnorder.ReturnOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - FX 销售退货单")
@RestController
@RequestMapping("/fx/return-order")
@Validated
public class ReturnOrderController {

    @Resource
    private ReturnOrderService returnOrderService;

    @PostMapping("/save")
    @Operation(summary = "保存销售单")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:create')")
    public CommonResult<Long> saveOrdersInfo(@Valid @RequestBody ReturnOrderSaveReqVO createReqVO) throws Exception {
        return success(returnOrderService.saveOrdersInfo(createReqVO));
    }

    @PostMapping("/submit")
    @Operation(summary = "创建FX 销售退货单")
    @PreAuthorize("@ss.hasPermission('fx:return-order:create')")
    public CommonResult<Long> submitReturnOrder(@Valid @RequestBody ReturnOrderSaveReqVO createReqVO) {
        return success(returnOrderService.submitReturnOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新FX 销售退货单")
    @PreAuthorize("@ss.hasPermission('fx:return-order:update')")
    public CommonResult<Boolean> updateReturnOrder(@Valid @RequestBody ReturnOrderSaveReqVO updateReqVO) {
        returnOrderService.updateReturnOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除FX 销售退货单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:return-order:delete')")
    public CommonResult<Boolean> deleteReturnOrder(@RequestParam("id") Long id) {
        returnOrderService.deleteReturnOrder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得FX 销售退货单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:return-order:query')")
    public CommonResult<ReturnOrdersInfoDetailRespVO> getReturnOrder(@RequestParam("id") Long id) {
        ReturnOrdersInfoDetailRespVO respVO = returnOrderService.getOrdersInfo(id);
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得FX 销售退货单分页")
    @PreAuthorize("@ss.hasPermission('fx:return-order:query')")
    public CommonResult<PageResult<ReturnOrderRespVO>> getReturnOrderPage(@Valid ReturnOrderPageReqVO pageReqVO) {
        PageResult<ReturnOrderDO> pageResult = returnOrderService.getReturnOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReturnOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出FX 销售退货单 Excel")
    @PreAuthorize("@ss.hasPermission('fx:return-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReturnOrderExcel(@Valid ReturnOrderPageReqVO pageReqVO,
                                       HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReturnOrderDO> list = returnOrderService.getReturnOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "FX 销售退货单.xls", "数据", ReturnOrderRespVO.class,
                BeanUtils.toBean(list, ReturnOrderRespVO.class));
    }

    // ==================== 流程相关 ====================
    @PostMapping("/cancel-by-start-user")
    @Operation(summary = "用户取消流程实例", description = "取消发起的流程")
    @PreAuthorize("@ss.hasPermission('fx:return-order:update')")
    public CommonResult<Boolean> cancelProcessInstance(@Valid @RequestBody ProcessInstanceCancelReqVO cancelReqVO) {
        returnOrderService.cancelProcessInstance(getLoginUserId(), cancelReqVO);
        return success(true);
    }

    @RequestMapping("/submit-by-start-user")
    @Operation(summary = "用户发起流程实例", description = "发起流程")
    @PreAuthorize("@ss.hasPermission('fx:return-order:update')")
    public CommonResult<Boolean> initProcess(@RequestParam("id") Long id) {
        returnOrderService.initProcess(id);
        return success(true);
    }

}