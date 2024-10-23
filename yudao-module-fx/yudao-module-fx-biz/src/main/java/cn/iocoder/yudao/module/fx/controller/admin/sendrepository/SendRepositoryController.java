package cn.iocoder.yudao.module.fx.controller.admin.sendrepository;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository.SendRepositoryDO;
import cn.iocoder.yudao.module.fx.service.sendrepository.SendRepositoryService;

@Tag(name = "管理后台 - FX 发货仓库")
@RestController
@RequestMapping("/fx/send-repository")
@Validated
public class SendRepositoryController {

    @Resource
    private SendRepositoryService sendRepositoryService;

    @PostMapping("/create")
    @Operation(summary = "创建FX 发货仓库")
    @PreAuthorize("@ss.hasPermission('fx:send-repository:create')")
    public CommonResult<Integer> createSendRepository(@Valid @RequestBody SendRepositorySaveReqVO createReqVO) {
        return success(sendRepositoryService.createSendRepository(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新FX 发货仓库")
    @PreAuthorize("@ss.hasPermission('fx:send-repository:update')")
    public CommonResult<Boolean> updateSendRepository(@Valid @RequestBody SendRepositorySaveReqVO updateReqVO) {
        sendRepositoryService.updateSendRepository(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除FX 发货仓库")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:send-repository:delete')")
    public CommonResult<Boolean> deleteSendRepository(@RequestParam("id") Integer id) {
        sendRepositoryService.deleteSendRepository(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得FX 发货仓库")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:send-repository:query')")
    public CommonResult<SendRepositoryRespVO> getSendRepository(@RequestParam("id") Integer id) {
        SendRepositoryDO sendRepository = sendRepositoryService.getSendRepository(id);
        return success(BeanUtils.toBean(sendRepository, SendRepositoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得FX 发货仓库分页")
    @PreAuthorize("@ss.hasPermission('fx:send-repository:query')")
    public CommonResult<PageResult<SendRepositoryRespVO>> getSendRepositoryPage(@Valid SendRepositoryPageReqVO pageReqVO) {
        PageResult<SendRepositoryDO> pageResult = sendRepositoryService.getSendRepositoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SendRepositoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出FX 发货仓库 Excel")
    @PreAuthorize("@ss.hasPermission('fx:send-repository:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSendRepositoryExcel(@Valid SendRepositoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SendRepositoryDO> list = sendRepositoryService.getSendRepositoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "FX 发货仓库.xls", "数据", SendRepositoryRespVO.class,
                        BeanUtils.toBean(list, SendRepositoryRespVO.class));
    }

}