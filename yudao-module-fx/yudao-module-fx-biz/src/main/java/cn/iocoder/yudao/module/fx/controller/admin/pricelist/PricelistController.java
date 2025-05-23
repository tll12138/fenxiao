package cn.iocoder.yudao.module.fx.controller.admin.pricelist;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.ImportPriceListExcelRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistBaseExcelVO;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistExcelVO;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.pricelist.PricelistDO;
import cn.iocoder.yudao.module.fx.service.pricelist.PricelistService;
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

@Tag(name = "管理后台 - 分销价格对照")
@RestController
@RequestMapping("/fx/pricelist")
@Validated
public class PricelistController {

    @Resource
    private PricelistService pricelistService;

    @PostMapping("/create")
    @Operation(summary = "创建分销价格对照")
    @PreAuthorize("@ss.hasPermission('fx:pricelist:create')")
    public CommonResult<Integer> createPricelist(@Valid @RequestBody PricelistSaveReqVO createReqVO) {
        return success(pricelistService.createPricelist(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新分销价格对照")
    @PreAuthorize("@ss.hasPermission('fx:pricelist:update')")
    public CommonResult<Boolean> updatePricelist(@Valid @RequestBody PricelistSaveReqVO updateReqVO) {
        pricelistService.updatePricelist(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分销价格对照")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:pricelist:delete')")
    public CommonResult<Boolean> deletePricelist(@RequestParam("id") Integer id) {
        pricelistService.deletePricelist(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得分销价格对照")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:pricelist:query')")
    public CommonResult<PricelistRespVO> getPricelist(@RequestParam("id") Integer id) {
        PricelistDO pricelist = pricelistService.getPricelist(id);
        return success(BeanUtils.toBean(pricelist, PricelistRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销价格对照分页")
    @PreAuthorize("@ss.hasPermission('fx:pricelist:query')")
    public CommonResult<PageResult<PricelistRespVO>> getPricelistPage(@Valid PricelistPageReqVO pageReqVO) {
        PageResult<PricelistDO> pageResult = pricelistService.getPricelistPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PricelistRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出分销价格对照 Excel")
    @PreAuthorize("@ss.hasPermission('fx:pricelist:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPricelistExcel(@Valid PricelistPageReqVO pageReqVO,
                                     HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PricelistDO> list = pricelistService.getPricelistPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销价格对照.xls", "数据", PricelistRespVO.class,
                BeanUtils.toBean(list, PricelistRespVO.class));
    }

    @GetMapping("/price-update")
    @Operation(summary = "分销商层级调整后触发，将价格对照表同SKU直接按基础更新")
    @PreAuthorize("@ss.hasPermission('fx:pricelist:update')")
    public CommonResult<Boolean> processPriceUpdate(@RequestParam("id") Integer id) {
        pricelistService.processPriceUpdate(id);
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入分销价格对照模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<PricelistExcelVO> list = new ArrayList<>();
        // 输出
        ExcelUtils.write(response, "分销控价导入模板.xls", "分销控价表", PricelistExcelVO.class, list);
    }

    @GetMapping("/get-import-base-template")
    @Operation(summary = "获得导入分销基础价格模板")
    public void importBaseTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<PricelistBaseExcelVO> list = new ArrayList<>();
        // 输出
        ExcelUtils.write(response, "分销基础价格导入模板.xls", "分销基础价格表", PricelistBaseExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入分销价格对照和基础价格")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "type", description = "导入类型：base-基础价格，其他-价格对照", example = "base"),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('fx:pricelist:import')")
    public CommonResult<ImportPriceListExcelRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                                @RequestParam(value = "type", defaultValue = "normal") String importType,
                                                                @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        if ("base".equalsIgnoreCase(importType)) {
            List<PricelistBaseExcelVO> list = ExcelUtils.read(file, PricelistBaseExcelVO.class);
            return success(pricelistService.importBasePriceList(list, updateSupport));
        } else {
            List<PricelistExcelVO> list = ExcelUtils.read(file, PricelistExcelVO.class);
            return success(pricelistService.importPriceList(list, updateSupport));
        }
    }

}