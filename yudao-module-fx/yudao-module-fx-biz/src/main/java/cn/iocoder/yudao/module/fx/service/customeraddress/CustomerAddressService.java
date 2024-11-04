package cn.iocoder.yudao.module.fx.service.customeraddress;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.customeraddress.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.fx.dal.dataobject.subcompanyinfo.SubCompanyInfoDO;
import com.github.yulichang.base.MPJBaseService;

import javax.validation.Valid;

/**
 * 分销商地址 Service 接口
 *
 * @author 管理员
 */
public interface CustomerAddressService extends MPJBaseService<CustomerAddressDO> {

    /**
     * 创建分销商地址
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomerAddress(@Valid CustomerAddressSaveReqVO createReqVO);

    /**
     * 更新分销商地址
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomerAddress(@Valid CustomerAddressSaveReqVO updateReqVO);

    /**
     * 删除分销商地址
     *
     * @param id 编号
     */
    void deleteCustomerAddress(Long id);

    /**
     * 获得分销商地址
     *
     * @param id 编号
     * @return 分销商地址
     */
    CustomerAddressDO getCustomerAddress(Long id);

    /**
     * 获得分销商地址分页
     *
     * @param pageReqVO 分页查询
     * @return 分销商地址分页
     */
    PageResult<CustomerAddressDO> getCustomerAddressPage(CustomerAddressPageReqVO pageReqVO);

}