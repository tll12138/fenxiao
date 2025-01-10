package cn.iocoder.yudao.module.fx.dal.mysql.goodsarchives;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.goodsarchives.vo.GoodsArchivesPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.goodsarchives.GoodsArchivesDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分销商品资料 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface GoodsArchivesMapper extends BaseMapperX<GoodsArchivesDO> {

    default PageResult<GoodsArchivesDO> selectPage(GoodsArchivesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<GoodsArchivesDO>()
                .eqIfPresent(GoodsArchivesDO::getSkuId, reqVO.getSkuId())
                .likeIfPresent(GoodsArchivesDO::getName, reqVO.getName())
                .likeIfPresent(GoodsArchivesDO::getShortName, reqVO.getShortName())
                .eqIfPresent(GoodsArchivesDO::getSalePrice, reqVO.getSalePrice())
                .eqIfPresent(GoodsArchivesDO::getCostPrice, reqVO.getCostPrice())
                .eqIfPresent(GoodsArchivesDO::getPropertiesValue, reqVO.getPropertiesValue())
                .eqIfPresent(GoodsArchivesDO::getCategory, reqVO.getCategory())
                .likeIfPresent(GoodsArchivesDO::getVcName, reqVO.getVcName())
                .eqIfPresent(GoodsArchivesDO::getItemType, reqVO.getItemType())
                .eqIfPresent(GoodsArchivesDO::getUnit, reqVO.getUnit())
                .eqIfPresent(GoodsArchivesDO::getSkuType, reqVO.getSkuType())
                .eqIfPresent(GoodsArchivesDO::getUpdateTime, reqVO.getUpdateTime())
                .likeIfPresent(GoodsArchivesDO::getBillingName, reqVO.getBillingName())
                .eqIfPresent(GoodsArchivesDO::getBrand, reqVO.getBrand())
                .eqIfPresent(GoodsArchivesDO::getPicBig, reqVO.getPicBig())
                .eqIfPresent(GoodsArchivesDO::getIId, reqVO.getIId())
                .eqIfPresent(GoodsArchivesDO::getMarketPrice, reqVO.getMarketPrice())
                .eqIfPresent(GoodsArchivesDO::getEnabled, reqVO.getEnabled())
                .eqIfPresent(GoodsArchivesDO::getIsFx, reqVO.getIsFx())
                .eqIfPresent(GoodsArchivesDO::getActualCostPrice, reqVO.getActualCostPrice())
                .eqIfPresent(GoodsArchivesDO::getSaleCostPrice, reqVO.getSaleCostPrice())
                .eqIfPresent(GoodsArchivesDO::getLevel1Category, reqVO.getLevel1Category())
                .eqIfPresent(GoodsArchivesDO::getLevel2Category, reqVO.getLevel2Category())
                .eqIfPresent(GoodsArchivesDO::getIsCount, reqVO.getIsCount())
                .eqIfPresent(GoodsArchivesDO::getIsFormal, reqVO.getIsFormal())
                .eqIfPresent(GoodsArchivesDO::getWeight, reqVO.getWeight())
                .eqIfPresent(GoodsArchivesDO::getScancode, reqVO.getScancode())
                .eqIfPresent(GoodsArchivesDO::getIsGroup, reqVO.getIsGroup())
                .orderByDesc(GoodsArchivesDO::getId));
    }

}