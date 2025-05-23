package cn.iocoder.yudao.module.fx.service.importorder;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderExcelRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderExcelVO;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.importorder.ImportOrderDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstorderout.JstOrderOutDTO;
import cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository.SendRepositoryDO;
import cn.iocoder.yudao.module.fx.dal.mysql.importorder.ImportOrderMapper;
import cn.iocoder.yudao.module.fx.service.customerinfo.CustomerInfoService;
import cn.iocoder.yudao.module.fx.service.sendrepository.SendRepositoryService;
import cn.iocoder.yudao.module.system.api.dict.DictDataApi;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.IMPORT_ORDER_EXISTS;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.IMPORT_ORDER_GENERATED_SALE;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.IMPORT_ORDER_IMPORT_LIST_IS_EMPTY;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.IMPORT_ORDER_NOT_EXISTS;

/**
 * 客商代发单 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ImportOrderServiceImpl implements ImportOrderService {

    @Resource
    private ImportOrderMapper importOrderMapper;
    @Resource
    private SendRepositoryService sendRepositoryService;
    @Resource
    private CustomerInfoService customerInfoService;
    @Resource
    private DictDataApi dataApi;

    @Override
    public Integer createImportOrder(ImportOrderSaveReqVO createReqVO) {
        // 插入
        ImportOrderDO importOrder = BeanUtils.toBean(createReqVO, ImportOrderDO.class);
        importOrderMapper.insert(importOrder);
        // 返回
        return importOrder.getId();
    }

    @Override
    public void updateImportOrder(ImportOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateImportOrderExists(updateReqVO.getId());
        // 更新
        ImportOrderDO updateObj = BeanUtils.toBean(updateReqVO, ImportOrderDO.class);
        importOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteImportOrder(Integer id) {
        // 校验存在
        validateImportOrderExists(id);
        // 删除
        importOrderMapper.deleteById(id);
    }

    private void validateImportOrderExists(Integer id) {
        if (importOrderMapper.selectById(id) == null) {
            throw exception(IMPORT_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public ImportOrderDO getImportOrder(Integer id) {
        return importOrderMapper.selectById(id);
    }

    @Override
    public PageResult<ImportOrderDO> getImportOrderPage(ImportOrderPageReqVO pageReqVO) {
        return importOrderMapper.selectPage(pageReqVO);
    }

    /**
     * 导入客商代发单数据
     *
     * @param list          导入数据列表，包含需要导入的客商代发单信息
     * @param updateSupport 是否支持更新已存在数据（true：更新；false：跳过）
     * @return 导入结果响应，包含成功与失败数量
     */
    @Override
    public ImportOrderExcelRespVO importOrderList(List<ImportOrderExcelVO> list, Boolean updateSupport) {
        if (CollUtil.isEmpty(list)) {
            throw exception(IMPORT_ORDER_IMPORT_LIST_IS_EMPTY);
        }

        ImportOrderExcelRespVO respVO = ImportOrderExcelRespVO.builder()
                .createSoIds(new ArrayList<>())
                .updateSoIds(new ArrayList<>())
                .failureSoIds(new LinkedHashMap<>())
                .build();

        // 预编译正则表达式提升性能
        final Pattern SO_ID_REGEX = Pattern.compile("^[^\u4e00-\u9fa5\\s]+$");

        for (ImportOrderExcelVO excelVO : list) {
            String soId = excelVO.getSoId();

            // 参数校验
            if (!validateSoId(soId, SO_ID_REGEX, respVO)) {
                continue;
            }
            try {
                ImportOrderDO orderDO = BeanUtils.toBean(excelVO, ImportOrderDO.class);

                // 字典转换逻辑
                dataApi.parseDictDataForOptional("fx_belong", excelVO.getBusinessAffiliation()).map(Integer::parseInt).ifPresent(orderDO::setBusinessAffiliation);
                dataApi.parseDictDataForOptional("yes_no", excelVO.getIsTraceless()).map(Integer::parseInt).ifPresent(orderDO::setIsTraceless);
                dataApi.parseDictDataForOptional("fx_wl", excelVO.getExpressCompany()).ifPresent(orderDO::setExpressCompanyId);
                dataApi.parseDictDataForOptional("fx_business_entity", excelVO.getPayingDistributor()).map(Integer::parseInt).ifPresent(orderDO::setPayingDistributorId);

                // 客户和仓库查询逻辑
                if (!processCustomerAndWarehouse(excelVO, orderDO, respVO, soId)) {
                    continue;
                }

                // 核心处理逻辑
                processOrderRecord(orderDO, updateSupport, respVO, soId);
            } catch (Exception e) {
                respVO.getFailureSoIds().put(soId, "数据处理异常: " + e.getMessage());
            }
        }
        return respVO;
    }


    private boolean validateSoId(String soId, Pattern pattern, ImportOrderExcelRespVO respVO) {
        if (!pattern.matcher(soId).matches()) {
            respVO.getFailureSoIds().put(soId, "单号包含中文或空格");
            return false;
        }
        if (soId.length() > 50) {
            respVO.getFailureSoIds().put(soId, "单号长度超过限制");
            return false;
        }
        return true;
    }

    private boolean processCustomerAndWarehouse(ImportOrderExcelVO excelVO, ImportOrderDO orderDO,
                                                ImportOrderExcelRespVO respVO, String soId) {
        // 客户查询逻辑
        CustomerInfoDO customer = customerInfoService.getCustomerInfoByName(excelVO.getCustomername());
        if (customer == null) {
            respVO.getFailureSoIds().put(soId, "客商不存在");
            return false;
        }
        orderDO.setCustomerid(customer.getId().toString());

        // 仓库查询逻辑
        SendRepositoryDO repository = sendRepositoryService.getSendRepositoryByName(excelVO.getWarehousename());
        if (repository == null) {
            respVO.getFailureSoIds().put(soId, "发货仓库不存在");
            return false;
        }
        orderDO.setWarehouseid(repository.getId().toString());
        return true;
    }

    private void processOrderRecord(ImportOrderDO orderDO, Boolean updateSupport,
                                    ImportOrderExcelRespVO respVO, String soId) {
        ImportOrderDO existOrder = importOrderMapper.selectOne(ImportOrderDO::getSoId, soId);

        if (existOrder == null) {
            importOrderMapper.insert(orderDO);
            respVO.getCreateSoIds().add(soId);
            return;
        }

        if (!updateSupport) {
            respVO.getFailureSoIds().put(soId, IMPORT_ORDER_EXISTS.getMsg());
            return;
        }

        if (existOrder.getIsSalesOrderGenerated() == 1) {
            respVO.getFailureSoIds().put(soId, IMPORT_ORDER_GENERATED_SALE.getMsg());
            return;
        }

        orderDO.setId(existOrder.getId());
        importOrderMapper.updateById(orderDO);
        respVO.getUpdateSoIds().add(soId);
    }

    /**
     * 根据发货单更新客商代发单
     *
     * @param dto
     */
    @Override
    public void updateImportOrderByJstOut(JstOrderOutDTO dto) {
        importOrderMapper.update(new LambdaUpdateWrapper<ImportOrderDO>()
                .set(ImportOrderDO::getIsShipped, 1)
                .set(ImportOrderDO::getTrackingNumber, dto.getExpress())
                .set(ImportOrderDO::getExpressCompany, dto.getExpressName())
                .eq(ImportOrderDO::getSoId, dto.getSoId()));
    }

}