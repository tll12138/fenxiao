package cn.iocoder.yudao.module.fx.controller.admin.subcompanyinfo;

import cn.iocoder.yudao.module.fx.convert.CustomerCovert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;
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

import cn.iocoder.yudao.module.fx.controller.admin.subcompanyinfo.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.subcompanyinfo.SubCompanyInfoDO;
import cn.iocoder.yudao.module.fx.service.subcompanyinfo.SubCompanyInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name = "管理后台 - 子公司信息")
@RestController
@RequestMapping("/fx/sub-company-info")
@Validated
public class SubCompanyInfoController {

    @Resource
    private SubCompanyInfoService subCompanyInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建子公司信息")
    @PreAuthorize("@ss.hasPermission('fx:sub-company-info:create')")
    public CommonResult<Long> createSubCompanyInfo(@Valid @RequestBody SubCompanyInfoSaveReqVO createReqVO) {
        return success(subCompanyInfoService.createSubCompanyInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新子公司信息")
    @PreAuthorize("@ss.hasPermission('fx:sub-company-info:update')")
    public CommonResult<Boolean> updateSubCompanyInfo(@Valid @RequestBody SubCompanyInfoSaveReqVO updateReqVO) {
        subCompanyInfoService.updateSubCompanyInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除子公司信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:sub-company-info:delete')")
    public CommonResult<Boolean> deleteSubCompanyInfo(@RequestParam("id") Long id) {
        subCompanyInfoService.deleteSubCompanyInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得子公司信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:sub-company-info:query')")
    public CommonResult<SubCompanyInfoRespVO> getSubCompanyInfo(@RequestParam("id") Long id) {
        SubCompanyInfoDO subCompanyInfo = subCompanyInfoService.getSubCompanyInfo(id);
        return success(BeanUtils.toBean(subCompanyInfo, SubCompanyInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得子公司信息分页")
    @PreAuthorize("@ss.hasPermission('fx:sub-company-info:query')")
    public CommonResult<PageResult<SubCompanyInfoRespVO>> getSubCompanyInfoPage(@Valid SubCompanyInfoPageReqVO pageReqVO) {
        PageResult<SubCompanyInfoDO> pageResult = subCompanyInfoService.getSubCompanyInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SubCompanyInfoRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得子公司信息,主要用于下拉框")
    @PreAuthorize("@ss.hasPermission('fx:sub-company-info:query')")
    public CommonResult<List<SubCompanyInfoRespVO>> getSubCompanyInfoList(@Valid SubCompanyInfoPageReqVO pageReqVO) {
        return success(subCompanyInfoService.getSubCompanyInfoList());
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出子公司信息 Excel")
    @PreAuthorize("@ss.hasPermission('fx:sub-company-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSubCompanyInfoExcel(@Valid SubCompanyInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SubCompanyInfoDO> list = subCompanyInfoService.getSubCompanyInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "子公司信息.xls", "数据", SubCompanyInfoRespVO.class,
                        BeanUtils.toBean(list, SubCompanyInfoRespVO.class));
    }

}