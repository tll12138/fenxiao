package cn.iocoder.yudao.module.fx.dal.mysql.jstaftersale;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.jstaftersale.vo.JstAfterSalePageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale.JstAfterSaleDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分销退货传聚水潭中间 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface JstAfterSaleMapper extends BaseMapperX<JstAfterSaleDO> {

    default PageResult<JstAfterSaleDO> selectPage(JstAfterSalePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<JstAfterSaleDO>()
                .eqIfPresent(JstAfterSaleDO::getShopId, reqVO.getShopId())
                .eqIfPresent(JstAfterSaleDO::getOuterAsId, reqVO.getOuterAsId())
                .eqIfPresent(JstAfterSaleDO::getSoId, reqVO.getSoId())
                .eqIfPresent(JstAfterSaleDO::getType, reqVO.getType())
                .eqIfPresent(JstAfterSaleDO::getLogisticsCompany, reqVO.getLogisticsCompany())
                .eqIfPresent(JstAfterSaleDO::getLId, reqVO.getLId())
                .eqIfPresent(JstAfterSaleDO::getShopStatus, reqVO.getShopStatus())
                .eqIfPresent(JstAfterSaleDO::getRemark, reqVO.getRemark())
                .eqIfPresent(JstAfterSaleDO::getGoodStatus, reqVO.getGoodStatus())
                .eqIfPresent(JstAfterSaleDO::getQuestionType, reqVO.getQuestionType())
                .eqIfPresent(JstAfterSaleDO::getTotalAmount, reqVO.getTotalAmount())
                .eqIfPresent(JstAfterSaleDO::getRefund, reqVO.getRefund())
                .eqIfPresent(JstAfterSaleDO::getPayment, reqVO.getPayment())
                .eqIfPresent(JstAfterSaleDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(JstAfterSaleDO::getAsId, reqVO.getAsId())
                .eqIfPresent(JstAfterSaleDO::getOId, reqVO.getOId())
                .eqIfPresent(JstAfterSaleDO::getWmsCoId, reqVO.getWmsCoId())
                .eqIfPresent(JstAfterSaleDO::getWarehouseType, reqVO.getWarehouseType())
                .eqIfPresent(JstAfterSaleDO::getReceiverCity, reqVO.getReceiverCity())
                .eqIfPresent(JstAfterSaleDO::getReceiverDistrict, reqVO.getReceiverDistrict())
                .eqIfPresent(JstAfterSaleDO::getExternalId, reqVO.getExternalId())
                .likeIfPresent(JstAfterSaleDO::getDrpCoName, reqVO.getDrpCoName())
                .eqIfPresent(JstAfterSaleDO::getWarehouse, reqVO.getWarehouse())
                .eqIfPresent(JstAfterSaleDO::getReceiverMobile, reqVO.getReceiverMobile())
                .eqIfPresent(JstAfterSaleDO::getReceiverState, reqVO.getReceiverState())
                .likeIfPresent(JstAfterSaleDO::getReceiverName, reqVO.getReceiverName())
                .eqIfPresent(JstAfterSaleDO::getReceiverAddress, reqVO.getReceiverAddress())
                .eqIfPresent(JstAfterSaleDO::getLabels, reqVO.getLabels())
                .betweenIfPresent(JstAfterSaleDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(JstAfterSaleDO::getToErpTime, reqVO.getToErpTime())
                .eqIfPresent(JstAfterSaleDO::getToErpMsg, reqVO.getToErpMsg())
                .eqIfPresent(JstAfterSaleDO::getOrderFrom, reqVO.getOrderFrom())
                .eqIfPresent(JstAfterSaleDO::getExcuteConfirming, reqVO.getExcuteConfirming())
                .eqIfPresent(JstAfterSaleDO::getLcId, reqVO.getLcId())
                .eqIfPresent(JstAfterSaleDO::getIoId, reqVO.getIoId())
                .eqIfPresent(JstAfterSaleDO::getSourceType, reqVO.getSourceType())
                .orderByDesc(JstAfterSaleDO::getId));
    }

}