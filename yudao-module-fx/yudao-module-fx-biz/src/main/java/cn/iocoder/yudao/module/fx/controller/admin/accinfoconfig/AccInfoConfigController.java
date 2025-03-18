package cn.iocoder.yudao.module.fx.controller.admin.accinfoconfig;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.accinfoconfig.vo.AccInfoConfigPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.accinfoconfig.vo.AccInfoConfigRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.accinfoconfig.vo.AccInfoConfigSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.accinfoconfig.AccInfoConfigDO;
import cn.iocoder.yudao.module.fx.service.accinfoconfig.AccInfoConfigService;
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

@Tag(name = "管理后台 - 客商账户初始化配置")
@RestController
@RequestMapping("/fx/acc-info-config")
@Validated
public class AccInfoConfigController {

    @Resource
    private AccInfoConfigService accInfoConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建客商账户初始化配置")
    @PreAuthorize("@ss.hasPermission('fx:acc-info-config:create')")
    public CommonResult<Integer> createAccInfoConfig(@Valid @RequestBody AccInfoConfigSaveReqVO createReqVO) {
        return success(accInfoConfigService.createAccInfoConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客商账户初始化配置")
    @PreAuthorize("@ss.hasPermission('fx:acc-info-config:update')")
    public CommonResult<Boolean> updateAccInfoConfig(@Valid @RequestBody AccInfoConfigSaveReqVO updateReqVO) {
        accInfoConfigService.updateAccInfoConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客商账户初始化配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:acc-info-config:delete')")
    public CommonResult<Boolean> deleteAccInfoConfig(@RequestParam("id") Integer id) {
        accInfoConfigService.deleteAccInfoConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得客商账户初始化配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:acc-info-config:query')")
    public CommonResult<AccInfoConfigRespVO> getAccInfoConfig(@RequestParam("id") Integer id) {
        AccInfoConfigDO accInfoConfig = accInfoConfigService.getAccInfoConfig(id);
        return success(BeanUtils.toBean(accInfoConfig, AccInfoConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得客商账户初始化配置分页")
    @PreAuthorize("@ss.hasPermission('fx:acc-info-config:query')")
    public CommonResult<PageResult<AccInfoConfigRespVO>> getAccInfoConfigPage(@Valid AccInfoConfigPageReqVO pageReqVO) {
        PageResult<AccInfoConfigDO> pageResult = accInfoConfigService.getAccInfoConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AccInfoConfigRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客商账户初始化配置 Excel")
    @PreAuthorize("@ss.hasPermission('fx:acc-info-config:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportAccInfoConfigExcel(@Valid AccInfoConfigPageReqVO pageReqVO,
                                         HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AccInfoConfigDO> list = accInfoConfigService.getAccInfoConfigPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "客商账户初始化配置.xls", "数据", AccInfoConfigRespVO.class,
                BeanUtils.toBean(list, AccInfoConfigRespVO.class));
    }

}