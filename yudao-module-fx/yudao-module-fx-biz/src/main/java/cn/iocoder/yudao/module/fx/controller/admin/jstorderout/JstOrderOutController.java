package cn.iocoder.yudao.module.fx.controller.admin.jstorderout;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.fx.controller.admin.jstorderout.vo.JstOrderOutSaveReqVO;
import cn.iocoder.yudao.module.fx.service.jstorderout.JstOrderOutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 聚水潭发货回传中间表")
@RestController
@RequestMapping("/fx/jst-order-out")
@Validated
public class JstOrderOutController {

    @Resource
    private JstOrderOutService jstOrderOutService;

    @PostMapping("/create")
    @Operation(summary = "创建聚水潭发货回传中间表")
    @PreAuthorize("@ss.hasPermission('fx:jst-order-out:create')")
    public CommonResult<Long> createJstOrderOut(@Valid @RequestBody JstOrderOutSaveReqVO createReqVO) {
        return success(jstOrderOutService.createJstOrderOut(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新聚水潭发货回传中间表")
    @PreAuthorize("@ss.hasPermission('fx:jst-order-out:update')")
    public CommonResult<Boolean> updateJstOrderOut(@Valid @RequestBody JstOrderOutSaveReqVO updateReqVO) {
        jstOrderOutService.updateJstOrderOut(updateReqVO);
        return success(true);
    }

}