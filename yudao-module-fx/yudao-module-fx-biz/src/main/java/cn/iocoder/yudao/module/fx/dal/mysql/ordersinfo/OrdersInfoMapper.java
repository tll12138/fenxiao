package cn.iocoder.yudao.module.fx.dal.mysql.ordersinfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 销售单 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface OrdersInfoMapper extends BaseMapperX<OrdersInfoDO> {

    default PageResult<OrdersInfoDO> selectPage(OrdersInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrdersInfoDO>()
                .eqIfPresent(OrdersInfoDO::getErpOrderNumber, reqVO.getErpOrderNumber())
                .eqIfPresent(OrdersInfoDO::getManager, reqVO.getManager())
                .eqIfPresent(OrdersInfoDO::getOrderId, reqVO.getOrderId())
                .betweenIfPresent(OrdersInfoDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(OrdersInfoDO::getRemark, reqVO.getRemark())
                .eqIfPresent(OrdersInfoDO::getLogisticsNumber, reqVO.getLogisticsNumber())
                .eqIfPresent(OrdersInfoDO::getExternalOrderNumber, reqVO.getExternalOrderNumber())
                .eqIfPresent(OrdersInfoDO::getOrderType, reqVO.getOrderType())
                .eqIfPresent(OrdersInfoDO::getDistributorId, reqVO.getDistributorId())
                .eqIfPresent(OrdersInfoDO::getBusinessBelong, reqVO.getBusinessBelong())
                .orderByDesc(OrdersInfoDO::getId));
    }

}