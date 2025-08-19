package cn.iocoder.yudao.module.fx.dal.mysql.ordersdetail;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.ordersdetail.vo.OrdersDetailPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 分销-销售订单明细 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface OrdersDetailMapper extends BaseMapperX<OrdersDetailDO> {

    default PageResult<OrdersDetailDO> selectPage(OrdersDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrdersDetailDO>()
                .eqIfPresent(OrdersDetailDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(OrdersDetailDO::getSaleType, reqVO.getSaleType())
                .eqIfPresent(OrdersDetailDO::getSkuId, reqVO.getSkuId())
                .likeIfPresent(OrdersDetailDO::getSkuName, reqVO.getSkuName())
                .eqIfPresent(OrdersDetailDO::getCategory, reqVO.getCategory())
                .eqIfPresent(OrdersDetailDO::getPrice, reqVO.getPrice())
                .eqIfPresent(OrdersDetailDO::getSalePrice, reqVO.getSalePrice())
                .eqIfPresent(OrdersDetailDO::getCount, reqVO.getCount())
                .eqIfPresent(OrdersDetailDO::getPriceAmount, reqVO.getPriceAmount())
                .eqIfPresent(OrdersDetailDO::getWeight, reqVO.getWeight())
                .eqIfPresent(OrdersDetailDO::getInventory, reqVO.getInventory())
                .orderByDesc(OrdersDetailDO::getId));
    }

    default List<OrdersDetailDO> selectListByOrderId(Long orderId) {
        return selectList(OrdersDetailDO::getOrderId, orderId);
    }

    default int deleteByOrderId(Long orderId) {
        return delete(OrdersDetailDO::getOrderId, orderId);
    }

    default void deleteAndInsertOrdersDetailByMainId(List<OrdersDetailDO> details) {
        Long orderId = details.get(0).getOrderId();
        deleteByOrderIdTruly(orderId);
        insertBatch(details);
    }

    void deleteByOrderIdTruly(Long orderId);

}