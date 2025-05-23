package cn.iocoder.yudao.module.fx.service.skucostprice;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.skucostprice.vo.ImportSkuCostPriceExcelRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.skucostprice.vo.SkuCostPriceExcelVO;
import cn.iocoder.yudao.module.fx.controller.admin.skucostprice.vo.SkuCostpricePageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.skucostprice.vo.SkuCostpriceSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.skucostprice.SkuCostpriceDO;
import cn.iocoder.yudao.module.fx.dal.mysql.skucostprice.SkuCostpriceMapper;
import cn.iocoder.yudao.module.fx.utils.ObjectUtils;
import cn.iocoder.yudao.module.system.api.dict.DictDataApi;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.IMPORT_SKU_COST_PRICE_IS_EMPTY;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.PRICE_LIST_EXISTS;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.SKU_COSTPRICE_NOT_EXISTS;

/**
 * 商品成本 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class SkuCostpriceServiceImpl implements SkuCostpriceService {

    @Resource
    private SkuCostpriceMapper skuCostpriceMapper;
    @Resource
    private DictDataApi dataApi;

    @Override
    public Long createSkuCostprice(SkuCostpriceSaveReqVO createReqVO) {
        // 插入
        SkuCostpriceDO skuCostprice = BeanUtils.toBean(createReqVO, SkuCostpriceDO.class);
        skuCostpriceMapper.insert(skuCostprice);
        // 返回
        return skuCostprice.getId();
    }

    @Override
    public void updateSkuCostprice(SkuCostpriceSaveReqVO updateReqVO) {
        // 校验存在
        validateSkuCostpriceExists(updateReqVO.getId());
        // 更新
        SkuCostpriceDO updateObj = BeanUtils.toBean(updateReqVO, SkuCostpriceDO.class);
        skuCostpriceMapper.updateById(updateObj);
    }

    @Override
    public void deleteSkuCostprice(Long id) {
        // 校验存在
        validateSkuCostpriceExists(id);
        // 删除
        skuCostpriceMapper.deleteById(id);
    }

    private void validateSkuCostpriceExists(Long id) {
        if (skuCostpriceMapper.selectById(id) == null) {
            throw exception(SKU_COSTPRICE_NOT_EXISTS);
        }
    }

    @Override
    public SkuCostpriceDO getSkuCostprice(Long id) {
        return skuCostpriceMapper.selectById(id);
    }

    @Override
    public PageResult<SkuCostpriceDO> getSkuCostpricePage(SkuCostpricePageReqVO pageReqVO) {
        return skuCostpriceMapper.selectPage(pageReqVO);
    }

    /**
     * 导入商品成本
     *
     * @param list
     * @param updateSupport
     * @return
     */
    @Override
    public ImportSkuCostPriceExcelRespVO importList(List<SkuCostPriceExcelVO> list, Boolean updateSupport) {
        if (CollUtil.isEmpty(list)) {
            throw exception(IMPORT_SKU_COST_PRICE_IS_EMPTY);
        }
        ImportSkuCostPriceExcelRespVO respVO = ImportSkuCostPriceExcelRespVO.builder()
                .createSkuIds(new ArrayList<>())
                .updateSkuIds(new ArrayList<>())
                .failureSkuIds(new LinkedHashMap<>())
                .build();
        for (SkuCostPriceExcelVO excelVO : list) {
            SkuCostpriceDO saveData = ObjectUtils.copyProperties(excelVO, SkuCostpriceDO.class);
            try {
                dataApi.parseDictDataForOptional("fx_brand", excelVO.getBrand()).ifPresent(saveData::setBrand);
                dataApi.parseDictDataForOptional("fx_goods_attributes", excelVO.getType()).map(Integer::parseInt).ifPresent(saveData::setType);
                dataApi.parseDictDataForOptional("fx_goods_nature", excelVO.getPaid()).map(Integer::parseInt).ifPresent(saveData::setPaid);

                processRecord(saveData, updateSupport, respVO);
            } catch (Exception e) {
                respVO.getFailureSkuIds().put(saveData.getSkuId(), "数据处理异常: " + e.getMessage());
            }
        }
        return respVO;
    }

    private void processRecord(SkuCostpriceDO saveData, Boolean updateSupport,
                               ImportSkuCostPriceExcelRespVO respVO) {
        String skuId = saveData.getSkuId();
        SkuCostpriceDO existDO = skuCostpriceMapper.selectOne(SkuCostpriceDO::getSkuId, saveData.getSkuId(), SkuCostpriceDO::getBrand, saveData.getBrand());

        if (existDO == null) {
            skuCostpriceMapper.insert(saveData);
            respVO.getCreateSkuIds().add(skuId);
            return;
        }
        if (!updateSupport) {
            respVO.getFailureSkuIds().put(skuId, PRICE_LIST_EXISTS.getMsg());
            return;
        }

        saveData.setId(existDO.getId());
        skuCostpriceMapper.updateById(saveData);
        respVO.getUpdateSkuIds().add(skuId);
    }

}