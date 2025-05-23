package cn.iocoder.yudao.module.fx.service.ec2jstorder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.ec2jstorder.vo.Ec2jstOrderPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ec2jstorder.vo.Ec2jstOrderSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder.Ec2jstOrderDO;

import javax.validation.Valid;

/**
 * 分销订单上传中间 Service 接口
 *
 * @author 管理员
 */
public interface Ec2jstOrderService {

    /**
     * 创建分销订单上传中间
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createEc2jstOrder(@Valid Ec2jstOrderSaveReqVO createReqVO);

    /**
     * 创建分销订单上传中间表
     *
     * @return 编号
     */
    Integer createEc2jstOrderByDO(Ec2jstOrderDO insertDO);

    /**
     * 更新分销订单上传中间
     *
     * @param updateReqVO 更新信息
     */
    void updateEc2jstOrder(@Valid Ec2jstOrderSaveReqVO updateReqVO);

    /**
     * 删除分销订单上传中间
     *
     * @param id 编号
     */
    void deleteEc2jstOrder(Integer id);

    /**
     * 获得分销订单上传中间
     *
     * @param id 编号
     * @return 分销订单上传中间
     */
    Ec2jstOrderDO getEc2jstOrder(Integer id);

    /**
     * 获得分销订单上传中间分页
     *
     * @param pageReqVO 分页查询
     * @return 分销订单上传中间分页
     */
    PageResult<Ec2jstOrderDO> getEc2jstOrderPage(Ec2jstOrderPageReqVO pageReqVO);

    /**
     * 定时订单上传任务
     */
    void uploadOrders();
}