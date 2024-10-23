package cn.iocoder.yudao.module.fx.service.customerinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import javax.validation.Valid;

/**
 * 分销商基础信息 Service 接口
 *
 * @author 管理员
 */
public interface CustomerInfoService {

    /**
     * 创建分销商基础信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomerInfo(@Valid CustomerInfoSaveReqVO createReqVO);

    /**
     * 更新分销商基础信息
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomerInfo(@Valid CustomerInfoSaveReqVO updateReqVO);

    /**
     * 删除分销商基础信息
     *
     * @param id 编号
     */
    void deleteCustomerInfo(Long id);

    /**
     * 获得分销商基础信息
     *
     * @param id 编号
     * @return 分销商基础信息
     */
    CustomerInfoDO getCustomerInfo(Long id);

    /**
     * 获得分销商详细基础信息
     *
     * @param id 编号
     * @return 分销商基础信息
     */
    CustomerInfoDetailRespVO getCustomerInfoDetail(Long id);

    /**
     * 获得分销商基础信息分页
     *
     * @param pageReqVO 分页查询
     * @return 分销商基础信息分页
     */
    PageResult<CustomerInfoDO> getCustomerInfoPage(CustomerInfoPageReqVO pageReqVO);

    /**
     * 获得分销商基础信息分页
     *
     * @param pageReqVO 分页查询
     * @return 分销商基础信息分页
     */
    PageResult<CustomerInfoDetailPageRespVO> getCustomerInfoDetailPage(CustomerInfoPageReqVO pageReqVO);

    // ==================== 子表（分销商账号） ====================

    /**
     * 获得分销商账号列表
     *
     * @param id ID
     * @return 分销商账号列表
     */
    List<CustomerAccountDO> getCustomerAccountListById(Long id);

    // ==================== 子表（分销商地址） ====================

    /**
     * 获得分销商地址列表
     *
     * @param id ID
     * @return 分销商地址列表
     */
    List<CustomerAddressDO> getCustomerAddressListById(Long id);

}