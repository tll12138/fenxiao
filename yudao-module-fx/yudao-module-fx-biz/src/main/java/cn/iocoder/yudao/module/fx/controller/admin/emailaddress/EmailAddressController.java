package cn.iocoder.yudao.module.fx.controller.admin.emailaddress;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.emailaddress.vo.EmailAddressPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.emailaddress.vo.EmailAddressRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.emailaddress.vo.EmailAddressSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.emailaddress.EmailAddressDO;
import cn.iocoder.yudao.module.fx.service.emailaddress.EmailAddressService;
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

@Tag(name = "管理后台 - 发票邮箱库")
@RestController
@RequestMapping("/fx/email-address")
@Validated
public class EmailAddressController {

    @Resource
    private EmailAddressService emailAddressService;

    @PostMapping("/create")
    @Operation(summary = "创建发票邮箱库")
    @PreAuthorize("@ss.hasPermission('fx:email-address:create')")
    public CommonResult<Integer> createEmailAddress(@Valid @RequestBody EmailAddressSaveReqVO createReqVO) {
        return success(emailAddressService.createEmailAddress(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新发票邮箱库")
    @PreAuthorize("@ss.hasPermission('fx:email-address:update')")
    public CommonResult<Boolean> updateEmailAddress(@Valid @RequestBody EmailAddressSaveReqVO updateReqVO) {
        emailAddressService.updateEmailAddress(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除发票邮箱库")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:email-address:delete')")
    public CommonResult<Boolean> deleteEmailAddress(@RequestParam("id") Integer id) {
        emailAddressService.deleteEmailAddress(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得发票邮箱库")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:email-address:query')")
    public CommonResult<EmailAddressRespVO> getEmailAddress(@RequestParam("id") Integer id) {
        EmailAddressDO emailAddress = emailAddressService.getEmailAddress(id);
        return success(BeanUtils.toBean(emailAddress, EmailAddressRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得发票邮箱库分页")
    @PreAuthorize("@ss.hasPermission('fx:email-address:query')")
    public CommonResult<PageResult<EmailAddressRespVO>> getEmailAddressPage(@Valid EmailAddressPageReqVO pageReqVO) {
        PageResult<EmailAddressDO> pageResult = emailAddressService.getEmailAddressPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, EmailAddressRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出发票邮箱库 Excel")
    @PreAuthorize("@ss.hasPermission('fx:email-address:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEmailAddressExcel(@Valid EmailAddressPageReqVO pageReqVO,
                                        HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<EmailAddressDO> list = emailAddressService.getEmailAddressPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "发票邮箱库.xls", "数据", EmailAddressRespVO.class,
                BeanUtils.toBean(list, EmailAddressRespVO.class));
    }

}