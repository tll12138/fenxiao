package cn.iocoder.yudao.module.fx.controller.admin.brandauth;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.brandauth.vo.BrandAuthPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.brandauth.vo.BrandAuthRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.brandauth.vo.BrandAuthSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.brandauth.BrandAuthDO;
import cn.iocoder.yudao.module.fx.service.brandauth.BrandAuthService;
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

@Tag(name = "管理后台 - 品牌授权")
@RestController
@RequestMapping("/fx/brand-auth")
@Validated
public class BrandAuthController {

    @Resource
    private BrandAuthService brandAuthService;

    @PostMapping("/create")
    @Operation(summary = "创建品牌授权")
    @PreAuthorize("@ss.hasPermission('fx:brand-auth:create')")
    public CommonResult<Integer> createBrandAuth(@Valid @RequestBody BrandAuthSaveReqVO createReqVO) {
        return success(brandAuthService.createBrandAuth(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新品牌授权")
    @PreAuthorize("@ss.hasPermission('fx:brand-auth:update')")
    public CommonResult<Boolean> updateBrandAuth(@Valid @RequestBody BrandAuthSaveReqVO updateReqVO) {
        brandAuthService.updateBrandAuth(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除品牌授权")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:brand-auth:delete')")
    public CommonResult<Boolean> deleteBrandAuth(@RequestParam("id") Integer id) {
        brandAuthService.deleteBrandAuth(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得品牌授权")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:brand-auth:query')")
    public CommonResult<BrandAuthRespVO> getBrandAuth(@RequestParam("id") Integer id) {
        BrandAuthDO brandAuth = brandAuthService.getBrandAuth(id);
        return success(BeanUtils.toBean(brandAuth, BrandAuthRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得品牌授权分页")
    @PreAuthorize("@ss.hasPermission('fx:brand-auth:query')")
    public CommonResult<PageResult<BrandAuthRespVO>> getBrandAuthPage(@Valid BrandAuthPageReqVO pageReqVO) {
        PageResult<BrandAuthDO> pageResult = brandAuthService.getBrandAuthPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BrandAuthRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出品牌授权 Excel")
    @PreAuthorize("@ss.hasPermission('fx:brand-auth:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBrandAuthExcel(@Valid BrandAuthPageReqVO pageReqVO,
                                     HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BrandAuthDO> list = brandAuthService.getBrandAuthPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "品牌授权.xls", "数据", BrandAuthRespVO.class,
                BeanUtils.toBean(list, BrandAuthRespVO.class));
    }

}