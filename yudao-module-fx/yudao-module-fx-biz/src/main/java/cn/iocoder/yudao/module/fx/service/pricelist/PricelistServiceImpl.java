package cn.iocoder.yudao.module.fx.service.pricelist;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.goodsarchives.GoodsArchivesDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.pricelist.PricelistDO;
import cn.iocoder.yudao.module.fx.dal.mysql.goodsarchives.GoodsArchivesMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.pricelist.PricelistMapper;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import com.diboot.core.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.PRICELIST_NOT_EXISTS;

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
        String brand = basePrice.getBrand();

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

}