package cn.iocoder.yudao.module.fx.dal.mysql.ordersinfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
                .eqIfPresent(OrdersInfoDO::getOrderStatus, reqVO.getOrderStatus())
                .orderByDesc(OrdersInfoDO::getId));
    }

    default PageResult<OrdersInfoDO> selectReturnPage(OrdersInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrdersInfoDO>()
                .eqIfPresent(OrdersInfoDO::getManager, reqVO.getManager())
                .eqIfPresent(OrdersInfoDO::getOrderId, reqVO.getOrderId())
                .betweenIfPresent(OrdersInfoDO::getOrderDate, reqVO.getOrderDate())
//                .neIfPresent(OrdersInfoDO::getOrderType, 3)
                .eqIfPresent(OrdersInfoDO::getDistributorId, reqVO.getDistributorId())
//                .eqIfPresent(OrdersInfoDO::getIsToErp, 1)
                .orderByDesc(OrdersInfoDO::getId));
    }

    /**
     * 获取所有跨境订单
     */
    List<OrdersInfoDO> getCrossBorderOrders();

    /**
     * 更新销售单更新订单状态
     *
     * @param id
     */
    void updateSaleMain(Long id);

    /**
     * 检验是否有小样
     *
     * @param id
     */
    Integer checkSample(Long id);

    /**
     * 检验是否满足箱规
     *
     * @param id
     */
    Integer checkBoxSize(Long id);

    /**
     * 获取所有自动发货订单（天猫国际、中免日上、猫超）
     */
    List<OrdersInfoDO> getAutoSendOrders();
}