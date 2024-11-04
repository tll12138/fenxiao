package cn.iocoder.yudao.module.fx.service.customeraddress;

import com.diboot.core.service.impl.BaseServiceImpl;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.customeraddress.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.customeraddress.CustomerAddressMapper;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 分销商地址 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class CustomerAddressServiceImpl extends MPJBaseServiceImpl<CustomerAddressMapper, CustomerAddressDO> implements CustomerAddressService {

    @Resource
    private CustomerAddressMapper customerAddressMapper;

    @Override
    public Long createCustomerAddress(CustomerAddressSaveReqVO createReqVO) {
        // 插入
        CustomerAddressDO customerAddress = BeanUtils.toBean(createReqVO, CustomerAddressDO.class);
        customerAddressMapper.insert(customerAddress);
        // 返回
        return customerAddress.getId();
    }

    @Override
    public void updateCustomerAddress(CustomerAddressSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerAddressExists(updateReqVO.getId());
        // 更新
        CustomerAddressDO updateObj = BeanUtils.toBean(updateReqVO, CustomerAddressDO.class);
        customerAddressMapper.updateById(updateObj);
    }

    @Override
    public void deleteCustomerAddress(Long id) {
        // 校验存在
        validateCustomerAddressExists(id);
        // 删除
        customerAddressMapper.deleteById(id);
    }

    private void validateCustomerAddressExists(Long id) {
        if (customerAddressMapper.selectById(id) == null) {
            throw exception(CUSTOMER_ADDRESS_NOT_EXISTS);
        }
    }

    @Override
    public CustomerAddressDO getCustomerAddress(Long id) {
        return customerAddressMapper.selectById(id);
    }

    @Override
    public PageResult<CustomerAddressDO> getCustomerAddressPage(CustomerAddressPageReqVO pageReqVO) {
        return customerAddressMapper.selectPage(pageReqVO);
    }

}