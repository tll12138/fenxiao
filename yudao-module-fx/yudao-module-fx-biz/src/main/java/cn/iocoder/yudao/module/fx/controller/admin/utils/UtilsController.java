package cn.iocoder.yudao.module.fx.controller.admin.utils;


import cn.iocoder.yudao.module.fx.controller.admin.utils.vo.AnalyzeAddressVo;
import cn.iocoder.yudao.module.fx.convert.CustomerCovert;
import cn.iocoder.yudao.module.fx.service.utils.UtilsService;
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


/**
 * @author zrl
 * @date 2024/7/26
 */
@Tag(name = "管理后台 - 工具类")
@RestController
@RequestMapping("/fx/utils")
@Validated
public class UtilsController {

    @Resource
    private UtilsService utilsService;

    @GetMapping("/analyze_address")
    @Operation(summary = "地址解析")
    public CommonResult<AnalyzeAddressVo> analyzeAddress(@RequestParam("text") String text) {
        return success(utilsService.analyzeAddress(text));
    }
}
