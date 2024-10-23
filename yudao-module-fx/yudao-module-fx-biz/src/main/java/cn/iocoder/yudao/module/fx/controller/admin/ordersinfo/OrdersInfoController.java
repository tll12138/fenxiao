package cn.iocoder.yudao.module.fx.controller.admin.ordersinfo;

import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.module.fx.service.ordersinfo.OrdersInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name = "管理后台 - 销售单")
@RestController
@RequestMapping("/fx/orders-info")
@Validated
public class OrdersInfoController {

    @Resource
    private OrdersInfoService ordersInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建销售单")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:create')")
    public CommonResult<Long> createOrdersInfo(@Valid @RequestBody OrdersInfoSaveReqVO createReqVO) {
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
    public CommonResult<OrdersInfoRespVO> getOrdersInfo(@RequestParam("id") Long id) {
        OrdersInfoDO ordersInfo = ordersInfoService.getOrdersInfo(id);
        return success(BeanUtils.toBean(ordersInfo, OrdersInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得销售单分页")
    @PreAuthorize("@ss.hasPermission('fx:orders-info:query')")
    public CommonResult<PageResult<OrdersInfoRespVO>> getOrdersInfoPage(@Valid OrdersInfoPageReqVO pageReqVO) {
        PageResult<OrdersInfoDO> pageResult = ordersInfoService.getOrdersInfoPage(pageReqVO);
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

}