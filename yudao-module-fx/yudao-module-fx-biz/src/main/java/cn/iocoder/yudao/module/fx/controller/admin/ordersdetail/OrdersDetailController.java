package cn.iocoder.yudao.module.fx.controller.admin.ordersdetail;

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

import cn.iocoder.yudao.module.fx.controller.admin.ordersdetail.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.service.ordersdetail.OrdersDetailService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name = "管理后台 - 分销-销售订单明细")
@RestController
@RequestMapping("/fx/orders-detail")
@Validated
public class OrdersDetailController {

    @Resource
    private OrdersDetailService ordersDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建分销-销售订单明细")
    @PreAuthorize("@ss.hasPermission('fx:orders-detail:create')")
    public CommonResult<Long> createOrdersDetail(@Valid @RequestBody OrdersDetailSaveReqVO createReqVO) {
        return success(ordersDetailService.createOrdersDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新分销-销售订单明细")
    @PreAuthorize("@ss.hasPermission('fx:orders-detail:update')")
    public CommonResult<Boolean> updateOrdersDetail(@Valid @RequestBody OrdersDetailSaveReqVO updateReqVO) {
        ordersDetailService.updateOrdersDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分销-销售订单明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:orders-detail:delete')")
    public CommonResult<Boolean> deleteOrdersDetail(@RequestParam("id") Long id) {
        ordersDetailService.deleteOrdersDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得分销-销售订单明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:orders-detail:query')")
    public CommonResult<OrdersDetailRespVO> getOrdersDetail(@RequestParam("id") Long id) {
        OrdersDetailDO ordersDetail = ordersDetailService.getOrdersDetail(id);
        return success(BeanUtils.toBean(ordersDetail, OrdersDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销-销售订单明细分页")
    @PreAuthorize("@ss.hasPermission('fx:orders-detail:query')")
    public CommonResult<PageResult<OrdersDetailRespVO>> getOrdersDetailPage(@Valid OrdersDetailPageReqVO pageReqVO) {
        PageResult<OrdersDetailDO> pageResult = ordersDetailService.getOrdersDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrdersDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出分销-销售订单明细 Excel")
    @PreAuthorize("@ss.hasPermission('fx:orders-detail:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOrdersDetailExcel(@Valid OrdersDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrdersDetailDO> list = ordersDetailService.getOrdersDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销-销售订单明细.xls", "数据", OrdersDetailRespVO.class,
                        BeanUtils.toBean(list, OrdersDetailRespVO.class));
    }

}