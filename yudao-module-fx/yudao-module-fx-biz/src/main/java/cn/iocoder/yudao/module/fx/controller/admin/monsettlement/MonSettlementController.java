package cn.iocoder.yudao.module.fx.controller.admin.monsettlement;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.monsettlement.vo.MonSettlementPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.monsettlement.vo.MonSettlementRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.monsettlement.vo.MonSettlementSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.monsettlement.MonSettlementDO;
import cn.iocoder.yudao.module.fx.service.monsettlement.MonSettlementService;
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

@Tag(name = "管理后台 - 分销账户月结")
@RestController
@RequestMapping("/fx/mon-settlement")
@Validated
public class MonSettlementController {

    @Resource
    private MonSettlementService monSettlementService;

    @PostMapping("/create")
    @Operation(summary = "创建分销账户月结")
    @PreAuthorize("@ss.hasPermission('fx:mon-settlement:create')")
    public CommonResult<Integer> createMonSettlement(@Valid @RequestBody MonSettlementSaveReqVO createReqVO) {
        return success(monSettlementService.createMonSettlement(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新分销账户月结")
    @PreAuthorize("@ss.hasPermission('fx:mon-settlement:update')")
    public CommonResult<Boolean> updateMonSettlement(@Valid @RequestBody MonSettlementSaveReqVO updateReqVO) {
        monSettlementService.updateMonSettlement(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分销账户月结")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:mon-settlement:delete')")
    public CommonResult<Boolean> deleteMonSettlement(@RequestParam("id") Integer id) {
        monSettlementService.deleteMonSettlement(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得分销账户月结")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:mon-settlement:query')")
    public CommonResult<MonSettlementRespVO> getMonSettlement(@RequestParam("id") Integer id) {
        MonSettlementDO monSettlement = monSettlementService.getMonSettlement(id);
        return success(BeanUtils.toBean(monSettlement, MonSettlementRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销账户月结分页")
    @PreAuthorize("@ss.hasPermission('fx:mon-settlement:query')")
    public CommonResult<PageResult<MonSettlementRespVO>> getMonSettlementPage(@Valid MonSettlementPageReqVO pageReqVO) {
        PageResult<MonSettlementDO> pageResult = monSettlementService.getMonSettlementPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MonSettlementRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出分销账户月结 Excel")
    @PreAuthorize("@ss.hasPermission('fx:mon-settlement:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMonSettlementExcel(@Valid MonSettlementPageReqVO pageReqVO,
                                         HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MonSettlementDO> list = monSettlementService.getMonSettlementPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销账户月结.xls", "数据", MonSettlementRespVO.class,
                BeanUtils.toBean(list, MonSettlementRespVO.class));
    }

}