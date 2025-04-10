package cn.iocoder.yudao.module.fx.dal.mysql.jstaftersaledata;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersaledata.JstAfterSaleDataDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.jstaftersaledata.vo.*;

/**
 * 分销退货传聚水潭明细中间 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface JstAfterSaleDataMapper extends BaseMapperX<JstAfterSaleDataDO> {

    default PageResult<JstAfterSaleDataDO> selectPage(JstAfterSaleDataPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<JstAfterSaleDataDO>()
                .eqIfPresent(JstAfterSaleDataDO::getMainId, reqVO.getMainId())
                .eqIfPresent(JstAfterSaleDataDO::getSkuId, reqVO.getSkuId())
                .eqIfPresent(JstAfterSaleDataDO::getQty, reqVO.getQty())
                .eqIfPresent(JstAfterSaleDataDO::getBatchId, reqVO.getBatchId())
                .eqIfPresent(JstAfterSaleDataDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(JstAfterSaleDataDO::getExpirationDate, reqVO.getExpirationDate())
                .eqIfPresent(JstAfterSaleDataDO::getSalePrice, reqVO.getSalePrice())
                .betweenIfPresent(JstAfterSaleDataDO::getProducedDate, reqVO.getProducedDate())
                .eqIfPresent(JstAfterSaleDataDO::getOuterOiId, reqVO.getOuterOiId())
                .eqIfPresent(JstAfterSaleDataDO::getAmount, reqVO.getAmount())
                .eqIfPresent(JstAfterSaleDataDO::getType, reqVO.getType())
                .likeIfPresent(JstAfterSaleDataDO::getName, reqVO.getName())
                .eqIfPresent(JstAfterSaleDataDO::getPic, reqVO.getPic())
                .eqIfPresent(JstAfterSaleDataDO::getPropertiesValue, reqVO.getPropertiesValue())
                .eqIfPresent(JstAfterSaleDataDO::getSourceType, reqVO.getSourceType())
                .betweenIfPresent(JstAfterSaleDataDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(JstAfterSaleDataDO::getId));
    }

}