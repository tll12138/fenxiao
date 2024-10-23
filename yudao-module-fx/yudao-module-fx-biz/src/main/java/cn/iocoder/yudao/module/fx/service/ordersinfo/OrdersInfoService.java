package cn.iocoder.yudao.module.fx.service.ordersinfo;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

import javax.validation.Valid;

/**
 * 销售单 Service 接口
 *
 * @author 管理员
 */
public interface OrdersInfoService {

    /**
     * 创建销售单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrdersInfo(@Valid OrdersInfoSaveReqVO createReqVO);

    /**
     * 更新销售单
     *
     * @param updateReqVO 更新信息
     */
    void updateOrdersInfo(@Valid OrdersInfoSaveReqVO updateReqVO);

    /**
     * 删除销售单
     *
     * @param id 编号
     */
    void deleteOrdersInfo(Long id);

    /**
     * 获得销售单
     *
     * @param id 编号
     * @return 销售单
     */
    OrdersInfoDO getOrdersInfo(Long id);

    /**
     * 获得销售单分页
     *
     * @param pageReqVO 分页查询
     * @return 销售单分页
     */
    PageResult<OrdersInfoDO> getOrdersInfoPage(OrdersInfoPageReqVO pageReqVO);

    // ==================== 子表（分销-销售订单明细） ====================

    /**
     * 获得分销-销售订单明细列表
     *
     * @param orderId 主表订单id
     * @return 分销-销售订单明细列表
     */
    List<OrdersDetailDO> getOrdersDetailListByOrderId(Long orderId);

}