package cn.iocoder.yudao.module.fx.controller.admin.amountadj;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.amountadj.vo.AmountAdjPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.amountadj.vo.AmountAdjRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.amountadj.vo.AmountAdjSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.amountadj.AmountAdjDO;
import cn.iocoder.yudao.module.fx.service.amountadj.AmountAdjService;
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

@Tag(name = "管理后台 - 分销账户资金调整记录")
@RestController
@RequestMapping("/fx/amount-adj")
@Validated
public class AmountAdjController {

    @Resource
    private AmountAdjService amountAdjService;

    @PostMapping("/create")
    @Operation(summary = "创建分销账户资金调整记录")
    @PreAuthorize("@ss.hasPermission('fx:amount-adj:create')")
    public CommonResult<Integer> createAmountAdj(@Valid @RequestBody AmountAdjSaveReqVO createReqVO) {
        return success(amountAdjService.createAmountAdj(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新分销账户资金调整记录")
    @PreAuthorize("@ss.hasPermission('fx:amount-adj:update')")
    public CommonResult<Boolean> updateAmountAdj(@Valid @RequestBody AmountAdjSaveReqVO updateReqVO) {
        amountAdjService.updateAmountAdj(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分销账户资金调整记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:amount-adj:delete')")
    public CommonResult<Boolean> deleteAmountAdj(@RequestParam("id") Integer id) {
        amountAdjService.deleteAmountAdj(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得分销账户资金调整记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:amount-adj:query')")
    public CommonResult<AmountAdjRespVO> getAmountAdj(@RequestParam("id") Integer id) {
        AmountAdjDO amountAdj = amountAdjService.getAmountAdj(id);
        return success(BeanUtils.toBean(amountAdj, AmountAdjRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销账户资金调整记录分页")
    @PreAuthorize("@ss.hasPermission('fx:amount-adj:query')")
    public CommonResult<PageResult<AmountAdjRespVO>> getAmountAdjPage(@Valid AmountAdjPageReqVO pageReqVO) {
        PageResult<AmountAdjDO> pageResult = amountAdjService.getAmountAdjPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AmountAdjRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出分销账户资金调整记录 Excel")
    @PreAuthorize("@ss.hasPermission('fx:amount-adj:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportAmountAdjExcel(@Valid AmountAdjPageReqVO pageReqVO,
                                     HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AmountAdjDO> list = amountAdjService.getAmountAdjPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销账户资金调整记录.xls", "数据", AmountAdjRespVO.class,
                BeanUtils.toBean(list, AmountAdjRespVO.class));
    }

}