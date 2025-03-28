package cn.iocoder.yudao.module.fx.controller.admin.skucostprice;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.skucostprice.vo.SkuCostpricePageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.skucostprice.vo.SkuCostpriceRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.skucostprice.vo.SkuCostpriceSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.skucostprice.SkuCostpriceDO;
import cn.iocoder.yudao.module.fx.service.skucostprice.SkuCostpriceService;
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

@Tag(name = "管理后台 - 商品成本")
@RestController
@RequestMapping("/fx/sku-costprice")
@Validated
public class SkuCostpriceController {

    @Resource
    private SkuCostpriceService skuCostpriceService;

    @PostMapping("/create")
    @Operation(summary = "创建商品成本")
    @PreAuthorize("@ss.hasPermission('fx:sku-costprice:create')")
    public CommonResult<Long> createSkuCostprice(@Valid @RequestBody SkuCostpriceSaveReqVO createReqVO) {
        return success(skuCostpriceService.createSkuCostprice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新商品成本")
    @PreAuthorize("@ss.hasPermission('fx:sku-costprice:update')")
    public CommonResult<Boolean> updateSkuCostprice(@Valid @RequestBody SkuCostpriceSaveReqVO updateReqVO) {
        skuCostpriceService.updateSkuCostprice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除商品成本")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:sku-costprice:delete')")
    public CommonResult<Boolean> deleteSkuCostprice(@RequestParam("id") Long id) {
        skuCostpriceService.deleteSkuCostprice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得商品成本")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:sku-costprice:query')")
    public CommonResult<SkuCostpriceRespVO> getSkuCostprice(@RequestParam("id") Long id) {
        SkuCostpriceDO skuCostprice = skuCostpriceService.getSkuCostprice(id);
        return success(BeanUtils.toBean(skuCostprice, SkuCostpriceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得商品成本分页")
    @PreAuthorize("@ss.hasPermission('fx:sku-costprice:query')")
    public CommonResult<PageResult<SkuCostpriceRespVO>> getSkuCostpricePage(@Valid SkuCostpricePageReqVO pageReqVO) {
        PageResult<SkuCostpriceDO> pageResult = skuCostpriceService.getSkuCostpricePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SkuCostpriceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出商品成本 Excel")
    @PreAuthorize("@ss.hasPermission('fx:sku-costprice:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSkuCostpriceExcel(@Valid SkuCostpricePageReqVO pageReqVO,
                                        HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SkuCostpriceDO> list = skuCostpriceService.getSkuCostpricePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "商品成本.xls", "数据", SkuCostpriceRespVO.class,
                BeanUtils.toBean(list, SkuCostpriceRespVO.class));
    }

}