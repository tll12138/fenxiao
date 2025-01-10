package cn.iocoder.yudao.module.fx.controller.admin.goodsarchives;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.goodsarchives.vo.GoodsArchivesPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.goodsarchives.vo.GoodsArchivesRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.goodsarchives.vo.GoodsArchivesSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.goodsarchives.GoodsArchivesDO;
import cn.iocoder.yudao.module.fx.service.goodsarchives.GoodsArchivesService;
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

@Tag(name = "管理后台 - 分销商品资料")
@RestController
@RequestMapping("/fx/goods-archives")
@Validated
public class GoodsArchivesController {

    @Resource
    private GoodsArchivesService goodsArchivesService;

    @PostMapping("/create")
    @Operation(summary = "创建分销商品资料")
    @PreAuthorize("@ss.hasPermission('fx:goods-archives:create')")
    public CommonResult<Integer> createGoodsArchives(@Valid @RequestBody GoodsArchivesSaveReqVO createReqVO) {
        return success(goodsArchivesService.createGoodsArchives(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新分销商品资料")
    @PreAuthorize("@ss.hasPermission('fx:goods-archives:update')")
    public CommonResult<Boolean> updateGoodsArchives(@Valid @RequestBody GoodsArchivesSaveReqVO updateReqVO) {
        goodsArchivesService.updateGoodsArchives(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分销商品资料")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:goods-archives:delete')")
    public CommonResult<Boolean> deleteGoodsArchives(@RequestParam("id") Integer id) {
        goodsArchivesService.deleteGoodsArchives(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得分销商品资料")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:goods-archives:query')")
    public CommonResult<GoodsArchivesRespVO> getGoodsArchives(@RequestParam("id") Integer id) {
        GoodsArchivesDO goodsArchives = goodsArchivesService.getGoodsArchives(id);
        return success(BeanUtils.toBean(goodsArchives, GoodsArchivesRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销商品资料分页")
    @PreAuthorize("@ss.hasPermission('fx:goods-archives:query')")
    public CommonResult<PageResult<GoodsArchivesRespVO>> getGoodsArchivesPage(@Valid GoodsArchivesPageReqVO pageReqVO) {
        PageResult<GoodsArchivesDO> pageResult = goodsArchivesService.getGoodsArchivesPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, GoodsArchivesRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出分销商品资料 Excel")
    @PreAuthorize("@ss.hasPermission('fx:goods-archives:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportGoodsArchivesExcel(@Valid GoodsArchivesPageReqVO pageReqVO,
                                         HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<GoodsArchivesDO> list = goodsArchivesService.getGoodsArchivesPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销商品资料.xls", "数据", GoodsArchivesRespVO.class,
                BeanUtils.toBean(list, GoodsArchivesRespVO.class));
    }

    @PostMapping("/sync")
    @Operation(summary = "同步两天内商品信息")
    @PreAuthorize("@ss.hasPermission('fx:goods-archives:update')")
    public CommonResult<Boolean> syncGoodsArchives() {
        goodsArchivesService.syncGoodsArchives(false);
        return success(Boolean.TRUE);
    }


    @PostMapping("/syncAll")
    @Operation(summary = "同步全量商品信息")
    @PreAuthorize("@ss.hasPermission('fx:goods-archives:update')")
    public CommonResult<Boolean> syncAllGoodsArchives() {
        goodsArchivesService.syncGoodsArchives(true);
        return success(Boolean.TRUE);
    }

}