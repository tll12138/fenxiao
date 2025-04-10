package cn.iocoder.yudao.module.fx.service.ordersinfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.ProcessInstanceCancelReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 销售单 Service 接口
 *
 * @author 管理员
 */
public interface OrdersInfoService {


    /**
     * 保存销售单
     *
     * @param createReqVO
     * @return
     */
    Long saveOrdersInfo(OrdersInfoSaveReqVO createReqVO) throws Exception;

    /**
     * 创建销售单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrdersInfo(@Valid OrdersInfoSaveReqVO createReqVO) throws Exception;

    /**
     * 创建销售单
     *
     * @return 编号
     */
    Long createOrdersInfoByDO(OrdersInfoDO ordersInfoDO);

    /**
     * 更新销售单
     *
     * @param updateReqVO 更新信息
     */
    void updateOrdersInfo(@Valid OrdersInfoSaveReqVO updateReqVO);

    /**
     * 更新销售单
     */
    void updateOrdersInfoByDO(@Valid OrdersInfoDO ordersInfoDO);

    /**
     * @param id
     * @param orderStatusType
     */
    void updateOrdersInfoStatus(Long id, Integer orderStatusType);

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
    OrdersInfoDetailRespVO getOrdersInfoById(Long id);

    /**
     * 获得销售单
     *
     * @return 销售单
     */
    OrdersInfoDO getOrdersInfoByOrderId(String orderId);

    /**
     * 根据流程编号获得销售单
     *
     * @param processInstanceId 流程编号
     * @return 销售单
     */
    OrdersInfoDetailRespVO getOrdersInfoByPIId(String processInstanceId);

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


    void cancelProcessInstance(Long loginUserId, ProcessInstanceCancelReqVO cancelReqVO);

    //获取未向聚水潭上传订单列表
    List<OrdersInfoDO> getUnUploadedOrders();

    /**
     * 销售单处理
     */
    void saleProcess();

    /**
     * 检查商品中是否存在小样
     *
     * @return
     */
    Boolean checkSample(Long id);

    /**
     * 检查商品是否满足箱规
     *
     * @return
     */
    Boolean checkBoxSize(Long id);


    /**
     * 获取自动发货的订单
     */
    List<OrdersInfoDO> getAutoSendOrders();
}