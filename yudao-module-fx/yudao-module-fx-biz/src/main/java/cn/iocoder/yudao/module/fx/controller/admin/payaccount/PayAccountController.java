package cn.iocoder.yudao.module.fx.controller.admin.payaccount;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.payaccount.vo.PayAccountPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.payaccount.vo.PayAccountRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.payaccount.vo.PayAccountSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.payaccount.PayAccountDO;
import cn.iocoder.yudao.module.fx.service.payaccount.PayAccountService;
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

@Tag(name = "管理后台 - 分销支付账户")
@RestController
@RequestMapping("/fx/pay-account")
@Validated
public class PayAccountController {

    @Resource
    private PayAccountService payAccountService;

    @PostMapping("/create")
    @Operation(summary = "创建分销支付账户")
    @PreAuthorize("@ss.hasPermission('fx:pay-account:create')")
    public CommonResult<Integer> createPayAccount(@Valid @RequestBody PayAccountSaveReqVO createReqVO) {
        return success(payAccountService.createPayAccount(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新分销支付账户")
    @PreAuthorize("@ss.hasPermission('fx:pay-account:update')")
    public CommonResult<Boolean> updatePayAccount(@Valid @RequestBody PayAccountSaveReqVO updateReqVO) {
        payAccountService.updatePayAccount(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分销支付账户")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:pay-account:delete')")
    public CommonResult<Boolean> deletePayAccount(@RequestParam("id") Integer id) {
        payAccountService.deletePayAccount(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得分销支付账户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:pay-account:query')")
    public CommonResult<PayAccountRespVO> getPayAccount(@RequestParam("id") Integer id) {
        PayAccountDO payAccount = payAccountService.getPayAccount(id);
        return success(BeanUtils.toBean(payAccount, PayAccountRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销支付账户分页")
    @PreAuthorize("@ss.hasPermission('fx:pay-account:query')")
    public CommonResult<PageResult<PayAccountRespVO>> getPayAccountPage(@Valid PayAccountPageReqVO pageReqVO) {
        PageResult<PayAccountDO> pageResult = payAccountService.getPayAccountPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PayAccountRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出分销支付账户 Excel")
    @PreAuthorize("@ss.hasPermission('fx:pay-account:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPayAccountExcel(@Valid PayAccountPageReqVO pageReqVO,
                                      HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PayAccountDO> list = payAccountService.getPayAccountPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销支付账户.xls", "数据", PayAccountRespVO.class,
                BeanUtils.toBean(list, PayAccountRespVO.class));
    }

}