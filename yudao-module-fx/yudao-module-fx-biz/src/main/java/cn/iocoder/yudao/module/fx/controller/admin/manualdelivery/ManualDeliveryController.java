package cn.iocoder.yudao.module.fx.controller.admin.manualdelivery;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.manualdelivery.vo.ManualDeliveryPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.manualdelivery.vo.ManualDeliveryRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.manualdelivery.vo.ManualDeliverySaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.manualdelivery.ManualDeliveryDO;
import cn.iocoder.yudao.module.fx.service.manualdelivery.ManualDeliveryService;
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

@Tag(name = "管理后台 - 手动发货信息")
@RestController
@RequestMapping("/fx/manual-delivery")
@Validated
public class ManualDeliveryController {

    @Resource
    private ManualDeliveryService manualDeliveryService;

    @PostMapping("/create")
    @Operation(summary = "创建手动发货信息")
    @PreAuthorize("@ss.hasPermission('fx:manual-delivery:create')")
    public CommonResult<Long> createManualDelivery(@Valid @RequestBody ManualDeliverySaveReqVO createReqVO) {
        return success(manualDeliveryService.createManualDelivery(createReqVO));
    }

    @PutMapping("/handle_shipment")
    @Operation(summary = "执行手动发货")
    @PreAuthorize("@ss.hasPermission('fx:manual-delivery:update')")
    public CommonResult<Boolean> handleManualShipment(@RequestParam("id") Long id) {
        manualDeliveryService.handleManualShipment(id);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新手动发货信息")
    @PreAuthorize("@ss.hasPermission('fx:manual-delivery:update')")
    public CommonResult<Boolean> updateManualDelivery(@Valid @RequestBody ManualDeliverySaveReqVO updateReqVO) {
        manualDeliveryService.updateManualDelivery(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除手动发货信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:manual-delivery:delete')")
    public CommonResult<Boolean> deleteManualDelivery(@RequestParam("id") Long id) {
        manualDeliveryService.deleteManualDelivery(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得手动发货信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:manual-delivery:query')")
    public CommonResult<ManualDeliveryRespVO> getManualDelivery(@RequestParam("id") Long id) {
        ManualDeliveryDO manualDelivery = manualDeliveryService.getManualDelivery(id);
        return success(BeanUtils.toBean(manualDelivery, ManualDeliveryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得手动发货信息分页")
    @PreAuthorize("@ss.hasPermission('fx:manual-delivery:query')")
    public CommonResult<PageResult<ManualDeliveryRespVO>> getManualDeliveryPage(@Valid ManualDeliveryPageReqVO pageReqVO) {
        PageResult<ManualDeliveryDO> pageResult = manualDeliveryService.getManualDeliveryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ManualDeliveryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出手动发货信息 Excel")
    @PreAuthorize("@ss.hasPermission('fx:manual-delivery:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportManualDeliveryExcel(@Valid ManualDeliveryPageReqVO pageReqVO,
                                          HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ManualDeliveryDO> list = manualDeliveryService.getManualDeliveryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "手动发货信息.xls", "数据", ManualDeliveryRespVO.class,
                BeanUtils.toBean(list, ManualDeliveryRespVO.class));
    }

}