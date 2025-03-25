package cn.iocoder.yudao.module.fx.controller.admin.bigcustomeraddress;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.bigcustomeraddress.vo.BigCustomerAddressPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.bigcustomeraddress.vo.BigCustomerAddressRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.bigcustomeraddress.vo.BigCustomerAddressSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.bigcustomeraddress.BigCustomerAddressDO;
import cn.iocoder.yudao.module.fx.service.bigcustomeraddress.BigCustomerAddressService;
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

@Tag(name = "管理后台 - 分销大客户地址")
@RestController
@RequestMapping("/fx/big-customer-address")
@Validated
public class BigCustomerAddressController {

    @Resource
    private BigCustomerAddressService bigCustomerAddressService;

    @PostMapping("/create")
    @Operation(summary = "创建分销大客户地址")
    @PreAuthorize("@ss.hasPermission('fx:big-customer-address:create')")
    public CommonResult<Long> createBigCustomerAddress(@Valid @RequestBody BigCustomerAddressSaveReqVO createReqVO) {
        return success(bigCustomerAddressService.createBigCustomerAddress(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新分销大客户地址")
    @PreAuthorize("@ss.hasPermission('fx:big-customer-address:update')")
    public CommonResult<Boolean> updateBigCustomerAddress(@Valid @RequestBody BigCustomerAddressSaveReqVO updateReqVO) {
        bigCustomerAddressService.updateBigCustomerAddress(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分销大客户地址")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:big-customer-address:delete')")
    public CommonResult<Boolean> deleteBigCustomerAddress(@RequestParam("id") Long id) {
        bigCustomerAddressService.deleteBigCustomerAddress(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得分销大客户地址")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:big-customer-address:query')")
    public CommonResult<BigCustomerAddressRespVO> getBigCustomerAddress(@RequestParam("id") Long id) {
        BigCustomerAddressDO bigCustomerAddress = bigCustomerAddressService.getBigCustomerAddress(id);
        return success(BeanUtils.toBean(bigCustomerAddress, BigCustomerAddressRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销大客户地址分页")
    @PreAuthorize("@ss.hasPermission('fx:big-customer-address:query')")
    public CommonResult<PageResult<BigCustomerAddressRespVO>> getBigCustomerAddressPage(@Valid BigCustomerAddressPageReqVO pageReqVO) {
        PageResult<BigCustomerAddressDO> pageResult = bigCustomerAddressService.getBigCustomerAddressPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BigCustomerAddressRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出分销大客户地址 Excel")
    @PreAuthorize("@ss.hasPermission('fx:big-customer-address:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBigCustomerAddressExcel(@Valid BigCustomerAddressPageReqVO pageReqVO,
                                              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BigCustomerAddressDO> list = bigCustomerAddressService.getBigCustomerAddressPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销大客户地址.xls", "数据", BigCustomerAddressRespVO.class,
                BeanUtils.toBean(list, BigCustomerAddressRespVO.class));
    }

}