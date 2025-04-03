package cn.iocoder.yudao.module.fx.dal.mysql.manualdelivery;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.manualdelivery.ManualDeliveryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.manualdelivery.vo.*;

/**
 * 手动发货信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ManualDeliveryMapper extends BaseMapperX<ManualDeliveryDO> {

    default PageResult<ManualDeliveryDO> selectPage(ManualDeliveryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ManualDeliveryDO>()
                .eqIfPresent(ManualDeliveryDO::getExpressCompany, reqVO.getExpressCompany())
                .likeIfPresent(ManualDeliveryDO::getExpressName, reqVO.getExpressName())
                .eqIfPresent(ManualDeliveryDO::getExpressId, reqVO.getExpressId())
                .eqIfPresent(ManualDeliveryDO::getExpress, reqVO.getExpress())
                .eqIfPresent(ManualDeliveryDO::getSoId, reqVO.getSoId())
                .eqIfPresent(ManualDeliveryDO::getSaleId, reqVO.getSaleId())
                .eqIfPresent(ManualDeliveryDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ManualDeliveryDO::getReason, reqVO.getReason())
                .betweenIfPresent(ManualDeliveryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ManualDeliveryDO::getId));
    }

}