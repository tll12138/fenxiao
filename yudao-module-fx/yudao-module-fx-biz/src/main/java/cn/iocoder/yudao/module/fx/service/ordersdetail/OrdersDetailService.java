package cn.iocoder.yudao.module.fx.service.ordersdetail;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.ordersdetail.vo.OrdersDetailPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersdetail.vo.OrdersDetailSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 分销-销售订单明细 Service 接口
 *
 * @author 管理员
 */
public interface OrdersDetailService {

    /**
     * 创建分销-销售订单明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrdersDetail(@Valid OrdersDetailSaveReqVO createReqVO);

    /**
     * 更新分销-销售订单明细
     *
     * @param updateReqVO 更新信息
     */
    void updateOrdersDetail(@Valid OrdersDetailSaveReqVO updateReqVO);

    /**
     * 删除分销-销售订单明细
     *
     * @param id 编号
     */
    void deleteOrdersDetail(Long id);

    /**
     * 获得分销-销售订单明细
     *
     * @param id 编号
     * @return 分销-销售订单明细
     */
    OrdersDetailDO getOrdersDetail(Long id);

    /**
     * 获得分销-销售订单明细分页
     *
     * @param pageReqVO 分页查询
     * @return 分销-销售订单明细分页
     */
    PageResult<OrdersDetailDO> getOrdersDetailPage(OrdersDetailPageReqVO pageReqVO);


    /**
     * 获得分销-销售订单明细list
     *
     * @return 分销-销售订单明细
     */
    List<OrdersDetailDO> getOrdersDetailByOrderId(Long orderId);

    /**
     * 批量更新
     *
     * @param saveDetailList
     */
    void updateBatchById(List<OrdersDetailDO> saveDetailList);

    /**
     * 批量保存
     *
     * @param details
     */
    void saveBatch(List<OrdersDetailDO> details);
}