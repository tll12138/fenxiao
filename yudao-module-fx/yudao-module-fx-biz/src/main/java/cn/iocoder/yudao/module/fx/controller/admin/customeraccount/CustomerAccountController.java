package cn.iocoder.yudao.module.fx.controller.admin.customeraccount;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.module.fx.service.customeraccount.CustomerAccountService;
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

@Tag(name = "管理后台 - 分销商账号")
@RestController
@RequestMapping("/fx/customer-account")
@Validated
public class CustomerAccountController {

    @Resource
    private CustomerAccountService customerAccountService;

    @PostMapping("/create")
    @Operation(summary = "创建分销商账号")
    @PreAuthorize("@ss.hasPermission('fx:customer-account:create')")
    public CommonResult<Long> createCustomerAccount(@Valid @RequestBody CustomerAccountSaveReqVO createReqVO) {
        return success(customerAccountService.createCustomerAccount(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新分销商账号")
    @PreAuthorize("@ss.hasPermission('fx:customer-account:update')")
    public CommonResult<Boolean> updateCustomerAccount(@Valid @RequestBody CustomerAccountSaveReqVO updateReqVO) {
        customerAccountService.updateCustomerAccount(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分销商账号")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:customer-account:delete')")
    public CommonResult<Boolean> deleteCustomerAccount(@RequestParam("id") Long id) {
        customerAccountService.deleteCustomerAccount(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得分销商账号")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:customer-account:query')")
    public CommonResult<CustomerAccountRespVO> getCustomerAccount(@RequestParam("id") Long id) {
        CustomerAccountDO customerAccount = customerAccountService.getCustomerAccount(id);
        return success(BeanUtils.toBean(customerAccount, CustomerAccountRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销商账号分页")
    @PreAuthorize("@ss.hasPermission('fx:customer-account:query')")
    public CommonResult<PageResult<CustomerAccountRespVO>> getCustomerAccountPage(@Valid CustomerAccountPageReqVO pageReqVO) {
        PageResult<CustomerAccountDO> pageResult = customerAccountService.getCustomerAccountPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CustomerAccountRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出分销商账号 Excel")
    @PreAuthorize("@ss.hasPermission('fx:customer-account:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCustomerAccountExcel(@Valid CustomerAccountPageReqVO pageReqVO,
                                           HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CustomerAccountDO> list = customerAccountService.getCustomerAccountPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销商账号.xls", "数据", CustomerAccountRespVO.class,
                BeanUtils.toBean(list, CustomerAccountRespVO.class));
    }


}