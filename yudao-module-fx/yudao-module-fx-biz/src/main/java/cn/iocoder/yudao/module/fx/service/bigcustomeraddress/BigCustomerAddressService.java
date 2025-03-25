package cn.iocoder.yudao.module.fx.service.bigcustomeraddress;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.bigcustomeraddress.vo.BigCustomerAddressPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.bigcustomeraddress.vo.BigCustomerAddressSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.bigcustomeraddress.BigCustomerAddressDO;

import javax.validation.Valid;

/**
 * 分销大客户地址 Service 接口
 *
 * @author 管理员
 */
public interface BigCustomerAddressService {

    /**
     * 创建分销大客户地址
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBigCustomerAddress(@Valid BigCustomerAddressSaveReqVO createReqVO);

    /**
     * 更新分销大客户地址
     *
     * @param updateReqVO 更新信息
     */
    void updateBigCustomerAddress(@Valid BigCustomerAddressSaveReqVO updateReqVO);

    /**
     * 更新分销大客户地址数量
     */
    void updateBigCustomerAddressCountById(Long id);

    /**
     * 删除分销大客户地址
     *
     * @param id 编号
     */
    void deleteBigCustomerAddress(Long id);

    /**
     * 获得分销大客户地址
     *
     * @param id 编号
     * @return 分销大客户地址
     */
    BigCustomerAddressDO getBigCustomerAddress(Long id);

    /**
     * 获得分销大客户地址分页
     *
     * @param pageReqVO 分页查询
     * @return 分销大客户地址分页
     */
    PageResult<BigCustomerAddressDO> getBigCustomerAddressPage(BigCustomerAddressPageReqVO pageReqVO);

}