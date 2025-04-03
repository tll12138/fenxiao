package cn.iocoder.yudao.module.fx.service.importorder;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderExcelRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderExcelVO;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.importorder.ImportOrderDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstorderout.JstOrderOutDTO;
import cn.iocoder.yudao.module.fx.dal.mysql.importorder.ImportOrderMapper;
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
        ImportOrderExcelRespVO respVO = ImportOrderExcelRespVO.builder().createSoIds(new ArrayList<>())
                .updateSoIds(new ArrayList<>()).failureSoIds(new LinkedHashMap<>()).build();
        list.forEach(ImportOrderExcelVO -> {
            String soId = ImportOrderExcelVO.getSoId();
            // 正则表达式，匹配不包含中文和空格的字符串
            final Pattern SO_ID_REGEX = Pattern.compile("^[^\u4e00-\u9fa5\\s]+$");
            if (!SO_ID_REGEX.matcher(soId).matches()) {
                respVO.getFailureSoIds().put(soId, "单号" + soId + "包含中文或空格");
                return;
            } else if (soId.length() > 50) {
                respVO.getFailureSoIds().put(soId, "单号长度超过限制（最大50字符）");
                return;
            }
            // 判断如果不存在，在进行插入
            ImportOrderDO existOrder = importOrderMapper.selectOne(ImportOrderDO::getSoId, soId);
            if (existOrder == null) {
                importOrderMapper.insert(BeanUtils.toBean(ImportOrderExcelVO, ImportOrderDO.class));
                respVO.getCreateSoIds().add(soId);
                return;
            }
            // 如果存在，判断是否允许更新
            if (!updateSupport) {
                respVO.getFailureSoIds().put(soId, IMPORT_ORDER_EXISTS.getMsg());
                return;
            }
            // 校验组合条件
            if (1 == existOrder.getIsSalesOrderGenerated()) {
                respVO.getFailureSoIds().put(soId, IMPORT_ORDER_GENERATED_SALE.getMsg());
            } else {
                // 所有校验通过后执行更新
                ImportOrderDO updateOrder = BeanUtils.toBean(ImportOrderExcelVO, ImportOrderDO.class);
                updateOrder.setId(existOrder.getId());
                importOrderMapper.updateById(updateOrder);
                respVO.getUpdateSoIds().add(soId);
            }
        });
        return respVO;
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