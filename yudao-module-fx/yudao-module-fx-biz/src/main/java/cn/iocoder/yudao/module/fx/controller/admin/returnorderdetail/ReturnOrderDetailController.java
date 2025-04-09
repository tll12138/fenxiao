package cn.iocoder.yudao.module.fx.controller.admin.returnorderdetail;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.returnorderdetail.vo.ReturnOrderDetailPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorderdetail.vo.ReturnOrderDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorderdetail.vo.ReturnOrderDetailSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorderdetail.ReturnOrderDetailDO;
import cn.iocoder.yudao.module.fx.service.returnorderdetail.ReturnOrderDetailService;
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

@Tag(name = "管理后台 - 销售退货详情")
@RestController
@RequestMapping("/fx/return-order-detail")
@Validated
public class ReturnOrderDetailController {

    @Resource
    private ReturnOrderDetailService returnOrderDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建销售退货详情")
    @PreAuthorize("@ss.hasPermission('fx:return-order-detail:create')")
    public CommonResult<Long> createReturnOrderDetail(@Valid @RequestBody ReturnOrderDetailSaveReqVO createReqVO) {
        return success(returnOrderDetailService.createReturnOrderDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新销售退货详情")
    @PreAuthorize("@ss.hasPermission('fx:return-order-detail:update')")
    public CommonResult<Boolean> updateReturnOrderDetail(@Valid @RequestBody ReturnOrderDetailSaveReqVO updateReqVO) {
        returnOrderDetailService.updateReturnOrderDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除销售退货详情")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:return-order-detail:delete')")
    public CommonResult<Boolean> deleteReturnOrderDetail(@RequestParam("id") Long id) {
        returnOrderDetailService.deleteReturnOrderDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得销售退货详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:return-order-detail:query')")
    public CommonResult<ReturnOrderDetailRespVO> getReturnOrderDetail(@RequestParam("id") Long id) {
        ReturnOrderDetailDO returnOrderDetail = returnOrderDetailService.getReturnOrderDetail(id);
        return success(BeanUtils.toBean(returnOrderDetail, ReturnOrderDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得销售退货详情分页")
    @PreAuthorize("@ss.hasPermission('fx:return-order-detail:query')")
    public CommonResult<PageResult<ReturnOrderDetailRespVO>> getReturnOrderDetailPage(@Valid ReturnOrderDetailPageReqVO pageReqVO) {
        PageResult<ReturnOrderDetailDO> pageResult = returnOrderDetailService.getReturnOrderDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReturnOrderDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出销售退货详情 Excel")
    @PreAuthorize("@ss.hasPermission('fx:return-order-detail:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReturnOrderDetailExcel(@Valid ReturnOrderDetailPageReqVO pageReqVO,
                                             HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReturnOrderDetailDO> list = returnOrderDetailService.getReturnOrderDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "销售退货详情.xls", "数据", ReturnOrderDetailRespVO.class,
                BeanUtils.toBean(list, ReturnOrderDetailRespVO.class));
    }

}