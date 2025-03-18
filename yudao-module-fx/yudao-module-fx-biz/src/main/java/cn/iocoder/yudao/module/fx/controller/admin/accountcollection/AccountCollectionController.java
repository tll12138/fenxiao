package cn.iocoder.yudao.module.fx.controller.admin.accountcollection;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.accountcollection.vo.AccountCollectionPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.accountcollection.vo.AccountCollectionRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.accountcollection.vo.AccountCollectionSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.accountcollection.AccountCollectionDO;
import cn.iocoder.yudao.module.fx.service.accountcollection.AccountCollectionService;
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

@Tag(name = "管理后台 - 分销账户收款记录")
@RestController
@RequestMapping("/fx/account-collection")
@Validated
public class AccountCollectionController {

    @Resource
    private AccountCollectionService accountCollectionService;

    @PostMapping("/create")
    @Operation(summary = "创建分销账户收款记录")
    @PreAuthorize("@ss.hasPermission('fx:account-collection:create')")
    public CommonResult<Integer> createAccountCollection(@Valid @RequestBody AccountCollectionSaveReqVO createReqVO) {
        return success(accountCollectionService.createAccountCollection(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新分销账户收款记录")
    @PreAuthorize("@ss.hasPermission('fx:account-collection:update')")
    public CommonResult<Boolean> updateAccountCollection(@Valid @RequestBody AccountCollectionSaveReqVO updateReqVO) {
        accountCollectionService.updateAccountCollection(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分销账户收款记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:account-collection:delete')")
    public CommonResult<Boolean> deleteAccountCollection(@RequestParam("id") Integer id) {
        accountCollectionService.deleteAccountCollection(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得分销账户收款记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:account-collection:query')")
    public CommonResult<AccountCollectionRespVO> getAccountCollection(@RequestParam("id") Integer id) {
        AccountCollectionDO accountCollection = accountCollectionService.getAccountCollection(id);
        return success(BeanUtils.toBean(accountCollection, AccountCollectionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销账户收款记录分页")
    @PreAuthorize("@ss.hasPermission('fx:account-collection:query')")
    public CommonResult<PageResult<AccountCollectionRespVO>> getAccountCollectionPage(@Valid AccountCollectionPageReqVO pageReqVO) {
        PageResult<AccountCollectionDO> pageResult = accountCollectionService.getAccountCollectionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AccountCollectionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出分销账户收款记录 Excel")
    @PreAuthorize("@ss.hasPermission('fx:account-collection:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportAccountCollectionExcel(@Valid AccountCollectionPageReqVO pageReqVO,
                                             HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AccountCollectionDO> list = accountCollectionService.getAccountCollectionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销账户收款记录.xls", "数据", AccountCollectionRespVO.class,
                BeanUtils.toBean(list, AccountCollectionRespVO.class));
    }

}