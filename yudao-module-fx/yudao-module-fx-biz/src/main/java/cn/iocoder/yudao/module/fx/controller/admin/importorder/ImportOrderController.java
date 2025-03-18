package cn.iocoder.yudao.module.fx.controller.admin.importorder;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderExcelRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderExcelVO;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.importorder.ImportOrderDO;
import cn.iocoder.yudao.module.fx.service.importorder.ImportOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 客商代发单")
@RestController
@RequestMapping("/fx/import-order")
@Validated
public class ImportOrderController {

    @Resource
    private ImportOrderService importOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建客商代发单")
    @PreAuthorize("@ss.hasPermission('fx:import-order:create')")
    public CommonResult<Integer> createImportOrder(@Valid @RequestBody ImportOrderSaveReqVO createReqVO) {
        return success(importOrderService.createImportOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客商代发单")
    @PreAuthorize("@ss.hasPermission('fx:import-order:update')")
    public CommonResult<Boolean> updateImportOrder(@Valid @RequestBody ImportOrderSaveReqVO updateReqVO) {
        importOrderService.updateImportOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客商代发单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:import-order:delete')")
    public CommonResult<Boolean> deleteImportOrder(@RequestParam("id") Integer id) {
        importOrderService.deleteImportOrder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得客商代发单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:import-order:query')")
    public CommonResult<ImportOrderRespVO> getImportOrder(@RequestParam("id") Integer id) {
        ImportOrderDO importOrder = importOrderService.getImportOrder(id);
        return success(BeanUtils.toBean(importOrder, ImportOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得客商代发单分页")
    @PreAuthorize("@ss.hasPermission('fx:import-order:query')")
    public CommonResult<PageResult<ImportOrderRespVO>> getImportOrderPage(@Valid ImportOrderPageReqVO pageReqVO) {
        PageResult<ImportOrderDO> pageResult = importOrderService.getImportOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ImportOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客商代发单 Excel")
    @PreAuthorize("@ss.hasPermission('fx:import-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportImportOrderExcel(@Valid ImportOrderPageReqVO pageReqVO,
                                       HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ImportOrderDO> list = importOrderService.getImportOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "客商代发单.xls", "数据", ImportOrderRespVO.class,
                BeanUtils.toBean(list, ImportOrderRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入客商代发模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<ImportOrderExcelVO> list = new ArrayList<>();
        // 输出
        ExcelUtils.write(response, "客商代发导入模板.xls", "客商代发", ImportOrderExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入客商批量代发")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('fx:import-order:import')")
    public CommonResult<ImportOrderExcelRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                            @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<ImportOrderExcelVO> list = ExcelUtils.read(file, ImportOrderExcelVO.class);
        return success(importOrderService.importOrderList(list, updateSupport));
    }

}