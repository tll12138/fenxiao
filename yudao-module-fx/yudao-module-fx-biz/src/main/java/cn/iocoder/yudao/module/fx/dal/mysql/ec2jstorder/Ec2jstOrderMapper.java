package cn.iocoder.yudao.module.fx.dal.mysql.ec2jstorder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.ec2jstorder.vo.Ec2jstOrderPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder.Ec2jstOrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分销订单上传中间 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface Ec2jstOrderMapper extends BaseMapperX<Ec2jstOrderDO> {

    default PageResult<Ec2jstOrderDO> selectPage(Ec2jstOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<Ec2jstOrderDO>()
                .eqIfPresent(Ec2jstOrderDO::getShopId, reqVO.getShopId())
                .betweenIfPresent(Ec2jstOrderDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(Ec2jstOrderDO::getShopStatus, reqVO.getShopStatus())
                .eqIfPresent(Ec2jstOrderDO::getShopBuyerId, reqVO.getShopBuyerId())
                .eqIfPresent(Ec2jstOrderDO::getReceiverState, reqVO.getReceiverState())
                .eqIfPresent(Ec2jstOrderDO::getReceiverCity, reqVO.getReceiverCity())
                .eqIfPresent(Ec2jstOrderDO::getReceiverDistrict, reqVO.getReceiverDistrict())
                .eqIfPresent(Ec2jstOrderDO::getReceiverAddress, reqVO.getReceiverAddress())
                .likeIfPresent(Ec2jstOrderDO::getReceiverName, reqVO.getReceiverName())
                .eqIfPresent(Ec2jstOrderDO::getReceiverPhone, reqVO.getReceiverPhone())
                .eqIfPresent(Ec2jstOrderDO::getReceiverMobile, reqVO.getReceiverMobile())
                .eqIfPresent(Ec2jstOrderDO::getPayAmount, reqVO.getPayAmount())
                .eqIfPresent(Ec2jstOrderDO::getFreight, reqVO.getFreight())
                .eqIfPresent(Ec2jstOrderDO::getRemark, reqVO.getRemark())
                .eqIfPresent(Ec2jstOrderDO::getIsCod, reqVO.getIsCod())
                .eqIfPresent(Ec2jstOrderDO::getShopModified, reqVO.getShopModified())
                .eqIfPresent(Ec2jstOrderDO::getLId, reqVO.getLId())
                .eqIfPresent(Ec2jstOrderDO::getLogisticsCompany, reqVO.getLogisticsCompany())
                .eqIfPresent(Ec2jstOrderDO::getQuestionDesc, reqVO.getQuestionDesc())
                .eqIfPresent(Ec2jstOrderDO::getSellerFlag, reqVO.getSellerFlag())
                .eqIfPresent(Ec2jstOrderDO::getLcId, reqVO.getLcId())
                .betweenIfPresent(Ec2jstOrderDO::getToErpTime, reqVO.getToErpTime())
                .eqIfPresent(Ec2jstOrderDO::getErpStatus, reqVO.getErpStatus())
                .eqIfPresent(Ec2jstOrderDO::getOrderNo, reqVO.getOrderNo())
                .eqIfPresent(Ec2jstOrderDO::getOId, reqVO.getOId())
                .eqIfPresent(Ec2jstOrderDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(Ec2jstOrderDO::getWarehouse, reqVO.getWarehouse())
                .eqIfPresent(Ec2jstOrderDO::getSoFrom, reqVO.getSoFrom())
                .eqIfPresent(Ec2jstOrderDO::getErrorMsg, reqVO.getErrorMsg())
                .eqIfPresent(Ec2jstOrderDO::getLinkWarehouse, reqVO.getLinkWarehouse())
                .eqIfPresent(Ec2jstOrderDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(Ec2jstOrderDO::getAllocateInId, reqVO.getAllocateInId())
                .eqIfPresent(Ec2jstOrderDO::getIsOut, reqVO.getIsOut())
                .eqIfPresent(Ec2jstOrderDO::getErrorNum, reqVO.getErrorNum())
                .orderByDesc(Ec2jstOrderDO::getId));
    }

    void updateBatchById(@Param("list") List<Ec2jstOrderDO> list);
}