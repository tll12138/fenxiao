package cn.iocoder.yudao.module.fx.controller.admin.accountadjust;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.accountadjust.vo.AccountAdjustPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.accountadjust.vo.AccountAdjustRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.accountadjust.vo.AccountAdjustSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.accountadjust.AccountAdjustDO;
import cn.iocoder.yudao.module.fx.service.accountadjust.AccountAdjustService;
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

@Tag(name = "管理后台 - 分销账户调整")
@RestController
@RequestMapping("/fx/account-adjust")
@Validated
public class AccountAdjustController {

    @Resource
    private AccountAdjustService accountAdjustService;

    @PostMapping("/create")
    @Operation(summary = "创建分销账户调整")
    @PreAuthorize("@ss.hasPermission('fx:account-adjust:create')")
    public CommonResult<Integer> createAccountAdjust(@Valid @RequestBody AccountAdjustSaveReqVO createReqVO) {
        return success(accountAdjustService.createAccountAdjust(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新分销账户调整")
    @PreAuthorize("@ss.hasPermission('fx:account-adjust:update')")
    public CommonResult<Boolean> updateAccountAdjust(@Valid @RequestBody AccountAdjustSaveReqVO updateReqVO) {
        accountAdjustService.updateAccountAdjust(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分销账户调整")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:account-adjust:delete')")
    public CommonResult<Boolean> deleteAccountAdjust(@RequestParam("id") Integer id) {
        accountAdjustService.deleteAccountAdjust(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得分销账户调整")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:account-adjust:query')")
    public CommonResult<AccountAdjustRespVO> getAccountAdjust(@RequestParam("id") Integer id) {
        AccountAdjustDO accountAdjust = accountAdjustService.getAccountAdjust(id);
        return success(BeanUtils.toBean(accountAdjust, AccountAdjustRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销账户调整分页")
    @PreAuthorize("@ss.hasPermission('fx:account-adjust:query')")
    public CommonResult<PageResult<AccountAdjustRespVO>> getAccountAdjustPage(@Valid AccountAdjustPageReqVO pageReqVO) {
        PageResult<AccountAdjustDO> pageResult = accountAdjustService.getAccountAdjustPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AccountAdjustRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出分销账户调整 Excel")
    @PreAuthorize("@ss.hasPermission('fx:account-adjust:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportAccountAdjustExcel(@Valid AccountAdjustPageReqVO pageReqVO,
                                         HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AccountAdjustDO> list = accountAdjustService.getAccountAdjustPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销账户调整.xls", "数据", AccountAdjustRespVO.class,
                BeanUtils.toBean(list, AccountAdjustRespVO.class));
    }

}