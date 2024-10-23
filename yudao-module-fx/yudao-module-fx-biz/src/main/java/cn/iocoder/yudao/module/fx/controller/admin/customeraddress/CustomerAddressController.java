package cn.iocoder.yudao.module.fx.controller.admin.customeraddress;

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

import cn.iocoder.yudao.module.fx.controller.admin.customeraddress.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;
import cn.iocoder.yudao.module.fx.service.customeraddress.CustomerAddressService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name = "管理后台 - 分销商地址")
@RestController
@RequestMapping("/fx/customer-address")
@Validated
public class CustomerAddressController {

    @Resource
    private CustomerAddressService customerAddressService;

    @PostMapping("/create")
    @Operation(summary = "创建分销商地址")
    @PreAuthorize("@ss.hasPermission('fx:customer-address:create')")
    public CommonResult<Long> createCustomerAddress(@Valid @RequestBody CustomerAddressSaveReqVO createReqVO) {
        return success(customerAddressService.createCustomerAddress(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新分销商地址")
    @PreAuthorize("@ss.hasPermission('fx:customer-address:update')")
    public CommonResult<Boolean> updateCustomerAddress(@Valid @RequestBody CustomerAddressSaveReqVO updateReqVO) {
        customerAddressService.updateCustomerAddress(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分销商地址")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:customer-address:delete')")
    public CommonResult<Boolean> deleteCustomerAddress(@RequestParam("id") Long id) {
        customerAddressService.deleteCustomerAddress(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得分销商地址")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:customer-address:query')")
    public CommonResult<CustomerAddressRespVO> getCustomerAddress(@RequestParam("id") Long id) {
        CustomerAddressDO customerAddress = customerAddressService.getCustomerAddress(id);
        return success(BeanUtils.toBean(customerAddress, CustomerAddressRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销商地址分页")
    @PreAuthorize("@ss.hasPermission('fx:customer-address:query')")
    public CommonResult<PageResult<CustomerAddressRespVO>> getCustomerAddressPage(@Valid CustomerAddressPageReqVO pageReqVO) {
        PageResult<CustomerAddressDO> pageResult = customerAddressService.getCustomerAddressPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CustomerAddressRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出分销商地址 Excel")
    @PreAuthorize("@ss.hasPermission('fx:customer-address:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCustomerAddressExcel(@Valid CustomerAddressPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CustomerAddressDO> list = customerAddressService.getCustomerAddressPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销商地址.xls", "数据", CustomerAddressRespVO.class,
                        BeanUtils.toBean(list, CustomerAddressRespVO.class));
    }

}