package cn.iocoder.yudao.module.fx.service.returnorder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrderPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrderSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorder.ReturnOrderDO;

import javax.validation.Valid;

/**
 * FX 销售退货单 Service 接口
 *
 * @author 员工
 */
public interface ReturnOrderService {

    /**
     * 创建FX 销售退货单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long submitReturnOrder(@Valid ReturnOrderSaveReqVO createReqVO);

    /**
     * 更新FX 销售退货单
     *
     * @param updateReqVO 更新信息
     */
    void updateReturnOrder(@Valid ReturnOrderSaveReqVO updateReqVO);

    /**
     * 删除FX 销售退货单
     *
     * @param id 编号
     */
    void deleteReturnOrder(Long id);

    /**
     * 获得FX 销售退货单
     *
     * @param id 编号
     * @return FX 销售退货单
     */
    ReturnOrderDO getReturnOrder(Long id);

    /**
     * 获得FX 销售退货单分页
     *
     * @param pageReqVO 分页查询
     * @return FX 销售退货单分页
     */
    PageResult<ReturnOrderDO> getReturnOrderPage(ReturnOrderPageReqVO pageReqVO);

    Long saveOrdersInfo(ReturnOrderSaveReqVO createReqVO);

    ReturnOrdersInfoDetailRespVO getOrdersInfo(Long id);
}