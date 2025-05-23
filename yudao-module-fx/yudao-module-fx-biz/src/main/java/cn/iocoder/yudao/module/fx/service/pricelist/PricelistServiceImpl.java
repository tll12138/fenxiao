package cn.iocoder.yudao.module.fx.service.pricelist;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.ImportPriceListExcelRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistBaseExcelVO;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistExcelVO;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.goodsarchives.GoodsArchivesDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.pricelist.PricelistDO;
import cn.iocoder.yudao.module.fx.dal.mysql.customerinfo.CustomerInfoMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.goodsarchives.GoodsArchivesMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.pricelist.PricelistMapper;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import cn.iocoder.yudao.module.fx.utils.ObjectUtils;
import cn.iocoder.yudao.module.system.api.dict.DictDataApi;
import com.diboot.core.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.IMPORT_PRICE_BASE_LIST_IS_EMPTY;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.IMPORT_PRICE_LIST_IS_EMPTY;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.PRICELIST_NOT_EXISTS;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.PRICE_LIST_EXISTS;

/**
 * 分销价格对照 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PricelistServiceImpl implements PricelistService {

    @Resource
    private PricelistMapper pricelistMapper;
    @Resource
    private GoodsArchivesMapper goodsArchivesMapper;
    @Resource
    private CustomerInfoMapper customerInfoMapper;
    @Resource
    private DictDataApi dataApi;

    @Override
    public Integer createPricelist(PricelistSaveReqVO createReqVO) {
        // 插入
        PricelistDO pricelist = BeanUtils.toBean(createReqVO, PricelistDO.class);
        pricelistMapper.insert(pricelist);
        // 返回
        return pricelist.getId();
    }

    @Override
    public void updatePricelist(PricelistSaveReqVO updateReqVO) {
        // 校验存在
        validatePricelistExists(updateReqVO.getId());
        // 更新
        PricelistDO updateObj = BeanUtils.toBean(updateReqVO, PricelistDO.class);
        pricelistMapper.updateById(updateObj);
    }

    @Override
    public void deletePricelist(Integer id) {
        // 校验存在
        validatePricelistExists(id);
        // 删除
        pricelistMapper.deleteById(id);
    }

    private void validatePricelistExists(Integer id) {
        if (pricelistMapper.selectById(id) == null) {
            throw exception(PRICELIST_NOT_EXISTS);
        }
    }

    @Override
    public PricelistDO getPricelist(Integer id) {
        return pricelistMapper.selectById(id);
    }

    @Override
    public PageResult<PricelistDO> getPricelistPage(PricelistPageReqVO pageReqVO) {
        return pricelistMapper.selectPage(pageReqVO);
    }

    @Override
    public void processPriceUpdate(Integer id) {
        // 1. 检查基础价格记录
        PricelistDO basePrice = pricelistMapper.selectBasePrice(id);
        if (basePrice == null) {
            throw new BusinessException("未找到此分销商下的价格对照表明细");
        }

        // 2. 获取基础价格参数
        String skuId = basePrice.getSkuId();
        BigDecimal salePrice = basePrice.getSaleprice();
        Integer level = basePrice.getDistributorLevel();
        String brand = basePrice.getBrandId();

        // 3. 批量更新价格
        pricelistMapper.batchUpdatePrices(level, skuId, brand, salePrice);

        // 4. 处理需要插入的记录
        List<String> customers = pricelistMapper.selectNeedInsertCustomers(level, brand, skuId);
        if (CollectionUtil.isNotEmpty(customers)) {
            GoodsArchivesDO one = goodsArchivesMapper.selectOne(GoodsArchivesDO::getSkuId, skuId);
            if (one == null) {
                throw new BusinessException("商品信息不存在: " + skuId);
            }

            List<PricelistDO> insertList = customers.stream()
                    .map(customer -> buildPriceList(customer, one, salePrice, level, brand))
                    .collect(Collectors.toList());

            // 批量插入
            pricelistMapper.insertBatch(insertList);
        }
    }

    /**
     * 导入分销价格对照
     *
     * @param list
     * @param updateSupport
     * @return
     */
    @Override
    public ImportPriceListExcelRespVO importPriceList(List<PricelistExcelVO> list, Boolean updateSupport) {
        if (CollUtil.isEmpty(list)) {
            throw exception(IMPORT_PRICE_LIST_IS_EMPTY);
        }
        ImportPriceListExcelRespVO respVO = buildResponseVO();
        for (PricelistExcelVO excelVO : list) {
            PricelistDO saveData = ObjectUtils.copyProperties(excelVO, PricelistDO.class);
            try {
                if (!processCustomerAndGoods(excelVO.getSkuId(), excelVO.getCustomer(), respVO, saveData, false)) {
                    continue;
                }
                saveData.setIsNormal("0");
                dataApi.parseDictDataForOptional("fx_brand", excelVO.getBrand()).ifPresent(saveData::setBrandId);

                processOrderRecord(saveData, updateSupport, respVO, false);
            } catch (Exception e) {
                respVO.getFailureSkuIds().put(saveData.getSkuId(), "数据处理异常: " + e.getMessage());
            }

        }
        return respVO;
    }

    /**
     * 导入分销基础价格
     */
    @Override
    public ImportPriceListExcelRespVO importBasePriceList(List<PricelistBaseExcelVO> list, Boolean updateSupport) {
        if (CollUtil.isEmpty(list)) {
            throw exception(IMPORT_PRICE_BASE_LIST_IS_EMPTY);
        }
        ImportPriceListExcelRespVO respVO = buildResponseVO();
        for (PricelistBaseExcelVO excelVO : list) {
            PricelistDO saveData = ObjectUtils.copyProperties(excelVO, PricelistDO.class);
            if (!processCustomerAndGoods(excelVO.getSkuId(), null, respVO, saveData, true)) {
                continue;
            }
            try {
                dataApi.parseDictDataForOptional("fx_brand", excelVO.getBrand()).ifPresent(saveData::setBrandId);
                dataApi.parseDictDataForOptional("fx_customer_level", excelVO.getDistributorLevel()).map(Integer::parseInt).ifPresent(saveData::setDistributorLevel);
                dataApi.parseDictDataForOptional("yes_no", excelVO.getIsNormal()).ifPresent(saveData::setIsNormal);

                processOrderRecord(saveData, updateSupport, respVO, true);
            } catch (Exception e) {
                respVO.getFailureSkuIds().put(saveData.getSkuId(), "数据处理异常: " + e.getMessage());
            }
        }

        return respVO;
    }

    private boolean processCustomerAndGoods(String skuId, String customerId, ImportPriceListExcelRespVO respVO, PricelistDO saveData, boolean ifBase) {
        GoodsArchivesDO goods = goodsArchivesMapper.selectOne(GoodsArchivesDO::getSkuId, skuId);
        if (goods == null) {
            respVO.getFailureSkuIds().put(skuId, "商品信息不存在");
            return false;
        }
        saveData.setName(goods.getName());
        saveData.setCategory(goods.getCategory());
        if (!ifBase) {
            CustomerInfoDO customer = customerInfoMapper.selectOne(CustomerInfoDO::getDistributorName, customerId);
            if (customer == null) {
                respVO.getFailureSkuIds().put(skuId, "分销商信息不存在");
                return false;
            }
            saveData.setCustomer(customer.getDistributorName());
            saveData.setDistributorLevel(customer.getDistributorLevel());
        }
        return true;
    }

    private void processOrderRecord(PricelistDO saveData, Boolean updateSupport,
                                    ImportPriceListExcelRespVO respVO, boolean ifBase) {
        String skuId = saveData.getSkuId();
        PricelistDO existPriceList;
        if (ifBase) {
            existPriceList = pricelistMapper.selectOne(PricelistDO::getBrandId, saveData.getBrandId(), PricelistDO::getSkuId, skuId, PricelistDO::getIsNormal, saveData.getIsNormal());
        } else {
            existPriceList = pricelistMapper.selectOne(PricelistDO::getCustomerId, saveData.getCustomerId(), PricelistDO::getSkuId, skuId);
        }
        if (existPriceList == null) {
            pricelistMapper.insert(saveData);
            respVO.getCreateSkuIds().add(skuId);
            return;
        }

        if (!updateSupport) {
            respVO.getFailureSkuIds().put(skuId, PRICE_LIST_EXISTS.getMsg());
            return;
        }

        saveData.setId(existPriceList.getId());
        pricelistMapper.updateById(saveData);
        respVO.getUpdateSkuIds().add(skuId);
    }

    private PricelistDO buildPriceList(String customer, GoodsArchivesDO goods, BigDecimal salePrice, Integer level, String brand) {
        PricelistDO priceDo = new PricelistDO();
        priceDo.setCustomer(customer);
        priceDo.setSkuId(goods.getSkuId());
        priceDo.setCategory(goods.getCategory());
        priceDo.setSaleprice(salePrice);
        priceDo.setDistributorLevel(level);
        priceDo.setBrand(brand);
        return priceDo;
    }

    // 公共响应对象构建
    private ImportPriceListExcelRespVO buildResponseVO() {
        return ImportPriceListExcelRespVO.builder()
                .createSkuIds(new ArrayList<>())
                .updateSkuIds(new ArrayList<>())
                .failureSkuIds(new LinkedHashMap<>())
                .build();
    }

}