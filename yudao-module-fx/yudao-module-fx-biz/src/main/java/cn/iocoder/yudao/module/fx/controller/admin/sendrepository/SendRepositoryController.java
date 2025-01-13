package cn.iocoder.yudao.module.fx.controller.admin.sendrepository;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo.SendRepositoryPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo.SendRepositoryRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo.SendRepositorySaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository.SendRepositoryDO;
import cn.iocoder.yudao.module.fx.service.sendrepository.SendRepositoryService;
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

    @PostMapping("/sync")
    @Operation(summary = "同步发货仓库信息")
    @PreAuthorize("@ss.hasPermission('fx:send-repository:update')")
    public CommonResult<Boolean> syncSendRepository() {
        sendRepositoryService.syncSendRepository();
        return success(Boolean.TRUE);
    }

}