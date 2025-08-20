package cn.iocoder.yudao.module.fx.controller.admin.billapply;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplyPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplyRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplySaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.billapply.BillApplyDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.billapply.BillApplyDetailDO;
import cn.iocoder.yudao.module.fx.service.billapply.BillApplyService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.EMPTY_REQUEST;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 发票申请")
@RestController
@RequestMapping("/fx/bill-apply")
@Validated
public class BillApplyController {

    @Resource
    private BillApplyService billApplyService;

    @PostMapping("/create")
    @Operation(summary = "创建发票申请")
    @PreAuthorize("@ss.hasPermission('fx:bill-apply:create')")
    public CommonResult<Integer> createBillApply(@Valid @RequestBody BillApplySaveReqVO createReqVO) {
        return success(billApplyService.createBillApply(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新发票申请")
    @PreAuthorize("@ss.hasPermission('fx:bill-apply:update')")
    public CommonResult<Boolean> updateBillApply(@Valid @RequestBody BillApplySaveReqVO updateReqVO) {
        billApplyService.updateBillApply(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除发票申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:bill-apply:delete')")
    public CommonResult<Boolean> deleteBillApply(@RequestParam("id") Integer id) {
        billApplyService.deleteBillApply(id);
        return success(true);
    }

    @PostMapping("/push")
    @Operation(summary = "推送发票申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:bill-apply:push')")
    public CommonResult<Boolean> pushBillApply(@RequestParam("id") Integer id) {
        billApplyService.pushBillApply(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得发票申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:bill-apply:query')")
    public CommonResult<BillApplyRespVO> getBillApply(@RequestParam("id") Integer id) {
        BillApplyDO billApply = billApplyService.getBillApply(id);
        return success(BeanUtils.toBean(billApply, BillApplyRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得发票申请分页")
    @PreAuthorize("@ss.hasPermission('fx:bill-apply:query')")
    public CommonResult<PageResult<BillApplyRespVO>> getBillApplyPage(@Valid BillApplyPageReqVO pageReqVO) {
        PageResult<BillApplyDO> pageResult = billApplyService.getBillApplyPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BillApplyRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出发票申请 Excel")
    @PreAuthorize("@ss.hasPermission('fx:bill-apply:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBillApplyExcel(@Valid BillApplyPageReqVO pageReqVO,
                                     HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BillApplyDO> list = billApplyService.getBillApplyPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "发票申请.xls", "数据", BillApplyRespVO.class,
                BeanUtils.toBean(list, BillApplyRespVO.class));
    }

    @PostMapping("/callback")
    @Operation(summary = "发票申请流程归档回调")
    public CommonResult<Boolean> getBillApplyCallback(@RequestParam("id") Integer id,
                                                      @RequestParam("soId") String soId,
                                                      @RequestParam("file") MultipartFile[] files) {
        if (files.length < 1) {
            return error(EMPTY_REQUEST);
        }
        billApplyService.handleBillApplyCallback(id, soId, files);
        return success(true);
    }

    // ==================== 子表（发票申请详情） ====================

    @GetMapping("/bill-apply-detail/list-by-main-id")
    @Operation(summary = "获得发票申请详情列表")
    @Parameter(name = "mainId", description = "主表id")
    @PreAuthorize("@ss.hasPermission('fx:bill-apply:query')")
    public CommonResult<List<BillApplyDetailDO>> getBillApplyDetailListByMainId(@RequestParam("mainId") Integer mainId) {
        return success(billApplyService.getBillApplyDetailListByMainId(mainId));
    }
}