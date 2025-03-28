package cn.iocoder.yudao.module.fx.dal.mysql.skucostprice;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.skucostprice.SkuCostpriceDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.skucostprice.vo.*;

/**
 * 商品成本 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface SkuCostpriceMapper extends BaseMapperX<SkuCostpriceDO> {

    default PageResult<SkuCostpriceDO> selectPage(SkuCostpricePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SkuCostpriceDO>()
                .eqIfPresent(SkuCostpriceDO::getBrand, reqVO.getBrand())
                .eqIfPresent(SkuCostpriceDO::getSkuId, reqVO.getSkuId())
                .likeIfPresent(SkuCostpriceDO::getName, reqVO.getName())
                .eqIfPresent(SkuCostpriceDO::getValue, reqVO.getValue())
                .eqIfPresent(SkuCostpriceDO::getType, reqVO.getType())
                .eqIfPresent(SkuCostpriceDO::getPaid, reqVO.getPaid())
                .eqIfPresent(SkuCostpriceDO::getCostPrice, reqVO.getCostPrice())
                .eqIfPresent(SkuCostpriceDO::getCostOtherprice, reqVO.getCostOtherprice())
                .eqIfPresent(SkuCostpriceDO::getOutCost, reqVO.getOutCost())
                .betweenIfPresent(SkuCostpriceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SkuCostpriceDO::getId));
    }

}