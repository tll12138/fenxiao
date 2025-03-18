package cn.iocoder.yudao.module.fx.dal.mysql.pricelist;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.pricelist.PricelistDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 分销价格对照 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PricelistMapper extends BaseMapperX<PricelistDO> {

    default PageResult<PricelistDO> selectPage(PricelistPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PricelistDO>()
                .eqIfPresent(PricelistDO::getCustomer, reqVO.getCustomer())
                .eqIfPresent(PricelistDO::getSkuId, reqVO.getSkuId())
                .eqIfPresent(PricelistDO::getCategory, reqVO.getCategory())
                .eqIfPresent(PricelistDO::getSaleprice, reqVO.getSaleprice())
                .eqIfPresent(PricelistDO::getDistributorLevel, reqVO.getDistributorLevel())
                .likeIfPresent(PricelistDO::getName, reqVO.getName())
                .eqIfPresent(PricelistDO::getIsNormal, reqVO.getIsNormal())
                .eqIfPresent(PricelistDO::getBrand, reqVO.getBrand())
                .betweenIfPresent(PricelistDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PricelistDO::getId));
    }

    PricelistDO selectBasePrice(Integer id);

    void batchUpdatePrices(@Param("level") Integer level,
                           @Param("skuId") String skuId,
                           @Param("brand") String brand,
                           @Param("salePrice") BigDecimal salePrice);

    List<String> selectNeedInsertCustomers(@Param("level") Integer level,
                                           @Param("brand") String brand,
                                           @Param("skuId") String skuId);
}