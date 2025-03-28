package cn.iocoder.yudao.module.fx.service.skucostprice;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.skucostprice.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.skucostprice.SkuCostpriceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.skucostprice.SkuCostpriceMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

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

}