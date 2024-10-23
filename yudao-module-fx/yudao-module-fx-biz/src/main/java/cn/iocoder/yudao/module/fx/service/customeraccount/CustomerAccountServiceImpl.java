package cn.iocoder.yudao.module.fx.service.customeraccount;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.customeraccount.CustomerAccountMapper;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 分销商账号 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class CustomerAccountServiceImpl implements CustomerAccountService {

    @Resource
    private CustomerAccountMapper customerAccountMapper;

    @Override
    public Long createCustomerAccount(CustomerAccountSaveReqVO createReqVO) {
        // 插入
        CustomerAccountDO customerAccount = BeanUtils.toBean(createReqVO, CustomerAccountDO.class);
        customerAccountMapper.insert(customerAccount);
        // 返回
        return customerAccount.getId();
    }

    @Override
    public void updateCustomerAccount(CustomerAccountSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerAccountExists(updateReqVO.getId());
        // 更新
        CustomerAccountDO updateObj = BeanUtils.toBean(updateReqVO, CustomerAccountDO.class);
        customerAccountMapper.updateById(updateObj);
    }

    @Override
    public void deleteCustomerAccount(Long id) {
        // 校验存在
        validateCustomerAccountExists(id);
        // 删除
        customerAccountMapper.deleteById(id);
    }

    private void validateCustomerAccountExists(Long id) {
        if (customerAccountMapper.selectById(id) == null) {
            throw exception(CUSTOMER_ACCOUNT_NOT_EXISTS);
        }
    }

    @Override
    public CustomerAccountDO getCustomerAccount(Long id) {
        return customerAccountMapper.selectById(id);
    }

    @Override
    public PageResult<CustomerAccountDO> getCustomerAccountPage(CustomerAccountPageReqVO pageReqVO) {
        return customerAccountMapper.selectPage(pageReqVO);
    }

}