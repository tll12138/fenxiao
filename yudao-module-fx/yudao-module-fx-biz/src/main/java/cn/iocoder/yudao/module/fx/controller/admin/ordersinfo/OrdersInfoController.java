package cn.iocoder.yudao.module.fx.controller.admin.ordersinfo;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.ProcessInstanceCancelReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.module.fx.service.ordersinfo.OrdersInfoService;
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

@Tag(name = "管理后台 - 销售单")
@RestController
@RequestMapping("/fx/orders-info")
@Validated
public class OrdersInfoController {

    @Resource
    private OrdersInfoService ordersInfoService;

    @PostMapping("/save")
    @Operation(summary = "保存销售单")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:create')")
    public CommonResult<Long> saveOrdersInfo(@Valid @RequestBody OrdersInfoSaveReqVO createReqVO) throws Exception {
        return success(ordersInfoService.saveOrdersInfo(createReqVO));
    }

    @PostMapping("/create")
    @Operation(summary = "创建销售单")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:create')")
    public CommonResult<Long> createOrdersInfo(@Valid @RequestBody OrdersInfoSaveReqVO createReqVO) throws Exception {
        return success(ordersInfoService.createOrdersInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新销售单")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:update')")
    public CommonResult<Boolean> updateOrdersInfo(@Valid @RequestBody OrdersInfoSaveReqVO updateReqVO) {
        ordersInfoService.updateOrdersInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除销售单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:orders-info:delete')")
    public CommonResult<Boolean> deleteOrdersInfo(@RequestParam("id") Long id) {
        ordersInfoService.deleteOrdersInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得销售单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:query')")
    public CommonResult<OrdersInfoDetailRespVO> getOrdersInfo(@RequestParam("id") Long id) {
        OrdersInfoDetailRespVO respVO = ordersInfoService.getOrdersInfoById(id);
        return success(respVO);
    }

    @GetMapping("/getByOrderId")
    @Operation(summary = "获得销售单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:query')")
    public CommonResult<OrdersInfoDetailRespVO> getOrdersInfoByOrderId(@RequestParam("orderId") String orderId) {
        OrdersInfoDetailRespVO respVO = ordersInfoService.getOrdersRespByOrderId(orderId);
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得销售单分页")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:query')")
    public CommonResult<PageResult<OrdersInfoRespVO>> getOrdersInfoPage(@Valid OrdersInfoPageReqVO pageReqVO) {
        PageResult<OrdersInfoDO> pageResult = ordersInfoService.getOrdersInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrdersInfoRespVO.class));
    }

    @GetMapping("/return_page")
    @Operation(summary = "获得销售单分页")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:query')")
    public CommonResult<PageResult<OrdersInfoRespVO>> getReturnOrdersInfoPage(@Valid OrdersInfoPageReqVO pageReqVO) {
        PageResult<OrdersInfoDO> pageResult = ordersInfoService.getReturnOrdersInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrdersInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出销售单 Excel")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOrdersInfoExcel(@Valid OrdersInfoPageReqVO pageReqVO,
                                      HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrdersInfoDO> list = ordersInfoService.getOrdersInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "销售单.xls", "数据", OrdersInfoRespVO.class,
                BeanUtils.toBean(list, OrdersInfoRespVO.class));
    }

    // ==================== 子表（分销-销售订单明细） ====================

    @GetMapping("/orders-detail/list-by-order-id")
    @Operation(summary = "获得分销-销售订单明细列表")
    @Parameter(name = "orderId", description = "主表订单id")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:query')")
    public CommonResult<List<OrdersDetailDO>> getOrdersDetailListByOrderId(@RequestParam("orderId") Long orderId) {
        return success(ordersInfoService.getOrdersDetailListByOrderId(orderId));
    }

    // ==================== 流程相关 ====================
    @PostMapping("/cancel-by-start-user")
    @Operation(summary = "用户取消流程实例", description = "取消发起的流程")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:update')")
    public CommonResult<Boolean> cancelProcessInstance(@Valid @RequestBody ProcessInstanceCancelReqVO cancelReqVO) {
        ordersInfoService.cancelProcessInstance(getLoginUserId(), cancelReqVO);
        return success(true);
    }

    @GetMapping("/start-by-start-user")
    @Operation(summary = "用户创建流程实例", description = "发起流程")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:update')")
    public CommonResult<Boolean> startProcessInstance(@RequestParam("id") Long id) {
        ordersInfoService.startProcessInstance(getLoginUserId(), id);
        return success(true);
    }

}