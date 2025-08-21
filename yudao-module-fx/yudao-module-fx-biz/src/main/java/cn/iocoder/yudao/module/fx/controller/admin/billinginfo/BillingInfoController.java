package cn.iocoder.yudao.module.fx.controller.admin.billinginfo;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.billinginfo.vo.BillingInfoPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.billinginfo.vo.BillingInfoRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.billinginfo.vo.BillingInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.billinginfo.BillingInfoDO;
import cn.iocoder.yudao.module.fx.service.billinginfo.BillingInfoService;
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

@Tag(name = "管理后台 - 开票信息")
@RestController
@RequestMapping("/fx/billing-info")
@Validated
public class BillingInfoController {

    @Resource
    private BillingInfoService billingInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建开票信息")
    @PreAuthorize("@ss.hasPermission('fx:billing-info:create')")
    public CommonResult<Integer> createBillingInfo(@Valid @RequestBody BillingInfoSaveReqVO createReqVO) {
        return success(billingInfoService.createBillingInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新开票信息")
    @PreAuthorize("@ss.hasPermission('fx:billing-info:update')")
    public CommonResult<Boolean> updateBillingInfo(@Valid @RequestBody BillingInfoSaveReqVO updateReqVO) {
        billingInfoService.updateBillingInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除开票信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:billing-info:delete')")
    public CommonResult<Boolean> deleteBillingInfo(@RequestParam("id") Integer id) {
        billingInfoService.deleteBillingInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得开票信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:billing-info:query')")
    public CommonResult<BillingInfoRespVO> getBillingInfo(@RequestParam("id") Integer id) {
        BillingInfoDO billingInfo = billingInfoService.getBillingInfo(id);
        return success(BeanUtils.toBean(billingInfo, BillingInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得开票信息分页")
    @PreAuthorize("@ss.hasPermission('fx:billing-info:query')")
    public CommonResult<PageResult<BillingInfoRespVO>> getBillingInfoPage(@Valid BillingInfoPageReqVO pageReqVO) {
        PageResult<BillingInfoDO> pageResult = billingInfoService.getBillingInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BillingInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出开票信息 Excel")
    @PreAuthorize("@ss.hasPermission('fx:billing-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBillingInfoExcel(@Valid BillingInfoPageReqVO pageReqVO,
                                       HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BillingInfoDO> list = billingInfoService.getBillingInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "开票信息.xls", "数据", BillingInfoRespVO.class,
                BeanUtils.toBean(list, BillingInfoRespVO.class));
    }

}