package cn.iocoder.yudao.module.fx.controller.admin.customerinfo;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoDetailPageRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoSyncVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import cn.iocoder.yudao.module.fx.service.customerinfo.CustomerInfoService;
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

@Tag(name = "管理后台 - 分销商基础信息")
@RestController
@RequestMapping("/fx/customer-info")
@Validated
public class CustomerInfoController {

    @Resource
    private CustomerInfoService customerInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建分销商基础信息")
    @PreAuthorize("@ss.hasPermission('fx:customer-info:create')")
    public CommonResult<Long> createCustomerInfo(@Valid @RequestBody CustomerInfoSaveReqVO createReqVO) {
        return success(customerInfoService.createCustomerInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新分销商基础信息")
    @PreAuthorize("@ss.hasPermission('fx:customer-info:update')")
    public CommonResult<Boolean> updateCustomerInfo(@Valid @RequestBody CustomerInfoSaveReqVO updateReqVO) {
        customerInfoService.updateCustomerInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分销商基础信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:customer-info:delete')")
    public CommonResult<Boolean> deleteCustomerInfo(@RequestParam("id") Long id) {
        customerInfoService.deleteCustomerInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得分销商基础信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:customer-info:query')")
    public CommonResult<CustomerInfoDetailRespVO> getCustomerInfo(@RequestParam("id") Long id) {
        return CommonResult.success(customerInfoService.getCustomerInfoDetail(id));
    }

    @GetMapping("/getAll")
    @Operation(summary = "获得全量分销商基础信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:customer-info:query')")
    public CommonResult<PageResult<CustomerInfoDetailPageRespVO>> getAllCustomerInfo() {
        List<CustomerInfoDO> allCustomerInfo = customerInfoService.getAllCustomerInfo();
        PageResult<CustomerInfoDO> customerInfoDOPageResult = new PageResult<>();
        customerInfoDOPageResult.setList(allCustomerInfo);
        customerInfoDOPageResult.setTotal(Long.valueOf(allCustomerInfo.size()));
        return success(BeanUtils.toBean(customerInfoDOPageResult, CustomerInfoDetailPageRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销商基础信息分页")
    @PreAuthorize("@ss.hasPermission('fx:customer-info:query')")
    public CommonResult<PageResult<CustomerInfoRespVO>> getCustomerInfoPage(@Valid CustomerInfoPageReqVO pageReqVO) {
        PageResult<CustomerInfoDO> pageResult = customerInfoService.getCustomerInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CustomerInfoRespVO.class));
    }

    @GetMapping("/page/detail")
    @Operation(summary = "获得分销商基础信息分页")
    @PreAuthorize("@ss.hasPermission('fx:customer-info:query')")
    public CommonResult<PageResult<CustomerInfoDetailPageRespVO>> getCustomerInfoDetailPage(@Valid CustomerInfoPageReqVO pageReqVO) {
        PageResult<CustomerInfoDetailPageRespVO> pageResult = customerInfoService.getCustomerInfoDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CustomerInfoDetailPageRespVO.class));
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出分销商基础信息 Excel")
    @PreAuthorize("@ss.hasPermission('fx:customer-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCustomerInfoExcel(@Valid CustomerInfoPageReqVO pageReqVO,
                                        HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CustomerInfoDO> list = customerInfoService.getCustomerInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销商基础信息.xls", "数据", CustomerInfoRespVO.class,
                BeanUtils.toBean(list, CustomerInfoRespVO.class));
    }

    @PostMapping("/sync")
    @Operation(summary = "同步客商信息")
    @PreAuthorize("@ss.hasPermission('fx:customer:update')")
    public CommonResult<Boolean> syncCustomerAccount() {
        customerInfoService.syncCustomers();
        return success(Boolean.TRUE);
    }

    @PostMapping("/syncOA")
    @Operation(summary = "同步OA客商信息")
    public CommonResult<Boolean> syncOaCustomerAccount(@RequestBody List<CustomerInfoSyncVO> syncVO) {
        customerInfoService.syncOaCustomers(syncVO);
        return success(Boolean.TRUE);
    }

    // ==================== 子表（分销商账号） ====================

    @GetMapping("/customer-account/list-by-id")
    @Operation(summary = "获得分销商账号列表")
    @Parameter(name = "id", description = "ID")
    @PreAuthorize("@ss.hasPermission('fx:customer-info:query')")
    public CommonResult<List<CustomerAccountDO>> getCustomerAccountListById(@RequestParam("id") Long id) {
        return success(customerInfoService.getCustomerAccountListById(id));
    }

    // ==================== 子表（分销商地址） ====================

    @GetMapping("/customer-address/list-by-id")
    @Operation(summary = "获得分销商地址列表")
    @Parameter(name = "id", description = "ID")
    @PreAuthorize("@ss.hasPermission('fx:customer-info:query')")
    public CommonResult<List<CustomerAddressDO>> getCustomerAddressListById(@RequestParam("id") Long id) {
        return success(customerInfoService.getCustomerAddressListById(id));
    }


}