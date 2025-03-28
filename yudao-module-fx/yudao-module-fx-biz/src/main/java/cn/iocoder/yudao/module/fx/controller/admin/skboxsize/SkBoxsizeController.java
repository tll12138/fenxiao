package cn.iocoder.yudao.module.fx.controller.admin.skboxsize;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.skboxsize.vo.SkBoxsizePageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.skboxsize.vo.SkBoxsizeRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.skboxsize.vo.SkBoxsizeSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.skboxsize.SkBoxsizeDO;
import cn.iocoder.yudao.module.fx.service.skboxsize.SkBoxsizeService;
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

@Tag(name = "管理后台 - 商品箱规")
@RestController
@RequestMapping("/fx/sk-boxsize")
@Validated
public class SkBoxsizeController {

    @Resource
    private SkBoxsizeService skBoxsizeService;

    @PostMapping("/create")
    @Operation(summary = "创建商品箱规")
    @PreAuthorize("@ss.hasPermission('fx:sk-boxsize:create')")
    public CommonResult<Long> createSkBoxsize(@Valid @RequestBody SkBoxsizeSaveReqVO createReqVO) {
        return success(skBoxsizeService.createSkBoxsize(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新商品箱规")
    @PreAuthorize("@ss.hasPermission('fx:sk-boxsize:update')")
    public CommonResult<Boolean> updateSkBoxsize(@Valid @RequestBody SkBoxsizeSaveReqVO updateReqVO) {
        skBoxsizeService.updateSkBoxsize(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除商品箱规")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:sk-boxsize:delete')")
    public CommonResult<Boolean> deleteSkBoxsize(@RequestParam("id") Long id) {
        skBoxsizeService.deleteSkBoxsize(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得商品箱规")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:sk-boxsize:query')")
    public CommonResult<SkBoxsizeRespVO> getSkBoxsize(@RequestParam("id") Long id) {
        SkBoxsizeDO skBoxsize = skBoxsizeService.getSkBoxsize(id);
        return success(BeanUtils.toBean(skBoxsize, SkBoxsizeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得商品箱规分页")
    @PreAuthorize("@ss.hasPermission('fx:sk-boxsize:query')")
    public CommonResult<PageResult<SkBoxsizeRespVO>> getSkBoxsizePage(@Valid SkBoxsizePageReqVO pageReqVO) {
        PageResult<SkBoxsizeDO> pageResult = skBoxsizeService.getSkBoxsizePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SkBoxsizeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出商品箱规 Excel")
    @PreAuthorize("@ss.hasPermission('fx:sk-boxsize:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSkBoxsizeExcel(@Valid SkBoxsizePageReqVO pageReqVO,
                                     HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SkBoxsizeDO> list = skBoxsizeService.getSkBoxsizePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "商品箱规.xls", "数据", SkBoxsizeRespVO.class,
                BeanUtils.toBean(list, SkBoxsizeRespVO.class));
    }

}