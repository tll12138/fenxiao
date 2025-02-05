package cn.iocoder.yudao.module.fx.controller.admin.inventorydata;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.inventorydata.vo.InventoryDataPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.inventorydata.vo.InventoryDataRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.inventorydata.vo.InventoryDataSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.inventorydata.InventoryDataDO;
import cn.iocoder.yudao.module.fx.service.inventorydata.InventoryDataService;
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

@Tag(name = "管理后台 - 分销商品库存")
@RestController
@RequestMapping("/fx/inventory-data")
@Validated
public class InventoryDataController {

    @Resource
    private InventoryDataService inventoryDataService;

    @PostMapping("/create")
    @Operation(summary = "创建分销商品库存")
    @PreAuthorize("@ss.hasPermission('fx:inventory-data:create')")
    public CommonResult<Integer> createInventoryData(@Valid @RequestBody InventoryDataSaveReqVO createReqVO) {
        return success(inventoryDataService.createInventoryData(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新分销商品库存")
    @PreAuthorize("@ss.hasPermission('fx:inventory-data:update')")
    public CommonResult<Boolean> updateInventoryData(@Valid @RequestBody InventoryDataSaveReqVO updateReqVO) {
        inventoryDataService.updateInventoryData(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分销商品库存")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:inventory-data:delete')")
    public CommonResult<Boolean> deleteInventoryData(@RequestParam("id") Integer id) {
        inventoryDataService.deleteInventoryData(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得分销商品库存")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:inventory-data:query')")
    public CommonResult<InventoryDataRespVO> getInventoryData(@RequestParam("id") Integer id) {
        InventoryDataDO inventoryData = inventoryDataService.getInventoryData(id);
        return success(BeanUtils.toBean(inventoryData, InventoryDataRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销商品库存分页")
    @PreAuthorize("@ss.hasPermission('fx:inventory-data:query')")
    public CommonResult<PageResult<InventoryDataRespVO>> getInventoryDataPage(@Valid InventoryDataPageReqVO pageReqVO) {
        PageResult<InventoryDataDO> pageResult = inventoryDataService.getInventoryDataPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InventoryDataRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出分销商品库存 Excel")
    @PreAuthorize("@ss.hasPermission('fx:inventory-data:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportInventoryDataExcel(@Valid InventoryDataPageReqVO pageReqVO,
                                         HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<InventoryDataDO> list = inventoryDataService.getInventoryDataPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销商品库存.xls", "数据", InventoryDataRespVO.class,
                BeanUtils.toBean(list, InventoryDataRespVO.class));
    }

    @PostMapping("/sync")
    @Operation(summary = "同步两天内库存信息")
    @PreAuthorize("@ss.hasPermission('fx:inventory-data:update')")
    public CommonResult<Boolean> syncGoodsArchives() throws InterruptedException {
        inventoryDataService.syncInventoryData();
        return success(Boolean.TRUE);
    }
}