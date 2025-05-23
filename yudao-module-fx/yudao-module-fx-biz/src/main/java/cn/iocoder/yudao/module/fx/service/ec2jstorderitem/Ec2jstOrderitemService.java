package cn.iocoder.yudao.module.fx.service.ec2jstorderitem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.ec2jstorderitem.vo.Ec2jstOrderitemPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ec2jstorderitem.vo.Ec2jstOrderitemSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorderitem.Ec2jstOrderitemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 分销订单上传详情中间 Service 接口
 *
 * @author 管理员
 */
public interface Ec2jstOrderitemService {

    /**
     * 创建分销订单上传详情中间
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    void createEc2jstOrderitem(@Valid Ec2jstOrderitemSaveReqVO createReqVO);

    /**
     * 获得分销订单上传详情中间
     *
     * @param id 编号
     * @return 分销订单上传详情中间
     */
    Ec2jstOrderitemDO getEc2jstOrderitem(String id);

    /**
     * 获得分销订单上传详情中间分页
     *
     * @param pageReqVO 分页查询
     * @return 分销订单上传详情中间分页
     */
    PageResult<Ec2jstOrderitemDO> getEc2jstOrderitemPage(Ec2jstOrderitemPageReqVO pageReqVO);

    //根据主表id查询子表数据
    List<Ec2jstOrderitemDO> getEc2jstOrderItemListByMainIds(List<Integer> mainIds);

    //批量插入子表数据
    void saveEc2jstOrderItemList(List<Ec2jstOrderitemDO> ec2jstOrderitemDOList);
}