package cn.iocoder.yudao.module.fx.controller.admin.pricelist;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.pricelist.PricelistDO;
import cn.iocoder.yudao.module.fx.service.pricelist.PricelistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
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
    public void processPriceUpdate(@RequestParam("id") Integer id) {
        pricelistService.processPriceUpdate(id);
    }

}