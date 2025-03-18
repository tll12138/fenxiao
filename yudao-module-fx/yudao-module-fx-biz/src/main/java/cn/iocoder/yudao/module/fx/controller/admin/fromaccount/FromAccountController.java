package cn.iocoder.yudao.module.fx.controller.admin.fromaccount;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.fromaccount.vo.FromAccountPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.fromaccount.vo.FromAccountRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.fromaccount.vo.FromAccountSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.fromaccount.FromAccountDO;
import cn.iocoder.yudao.module.fx.service.fromaccount.FromAccountService;
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

@Tag(name = "管理后台 -  分销打款账户")
@RestController
@RequestMapping("/fx/from-account")
@Validated
public class FromAccountController {

    @Resource
    private FromAccountService fromAccountService;

    @PostMapping("/create")
    @Operation(summary = "创建 分销打款账户")
    @PreAuthorize("@ss.hasPermission('fx:from-account:create')")
    public CommonResult<Integer> createFromAccount(@Valid @RequestBody FromAccountSaveReqVO createReqVO) {
        return success(fromAccountService.createFromAccount(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新 分销打款账户")
    @PreAuthorize("@ss.hasPermission('fx:from-account:update')")
    public CommonResult<Boolean> updateFromAccount(@Valid @RequestBody FromAccountSaveReqVO updateReqVO) {
        fromAccountService.updateFromAccount(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除 分销打款账户")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:from-account:delete')")
    public CommonResult<Boolean> deleteFromAccount(@RequestParam("id") Integer id) {
        fromAccountService.deleteFromAccount(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得 分销打款账户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:from-account:query')")
    public CommonResult<FromAccountRespVO> getFromAccount(@RequestParam("id") Integer id) {
        FromAccountDO fromAccount = fromAccountService.getFromAccount(id);
        return success(BeanUtils.toBean(fromAccount, FromAccountRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得 分销打款账户分页")
    @PreAuthorize("@ss.hasPermission('fx:from-account:query')")
    public CommonResult<PageResult<FromAccountRespVO>> getFromAccountPage(@Valid FromAccountPageReqVO pageReqVO) {
        PageResult<FromAccountDO> pageResult = fromAccountService.getFromAccountPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, FromAccountRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出 分销打款账户 Excel")
    @PreAuthorize("@ss.hasPermission('fx:from-account:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportFromAccountExcel(@Valid FromAccountPageReqVO pageReqVO,
                                       HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<FromAccountDO> list = fromAccountService.getFromAccountPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, " 分销打款账户.xls", "数据", FromAccountRespVO.class,
                BeanUtils.toBean(list, FromAccountRespVO.class));
    }

}