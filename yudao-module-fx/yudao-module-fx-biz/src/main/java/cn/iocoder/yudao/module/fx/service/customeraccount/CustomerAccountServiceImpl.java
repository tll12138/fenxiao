package cn.iocoder.yudao.module.fx.service.customeraccount;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.accinfoconfig.AccInfoConfigDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import cn.iocoder.yudao.module.fx.dal.mysql.customeraccount.CustomerAccountMapper;
import cn.iocoder.yudao.module.fx.service.accinfoconfig.AccInfoConfigService;
import cn.iocoder.yudao.module.fx.service.customerinfo.CustomerInfoService;
import cn.iocoder.yudao.module.system.api.dict.DictDataApi;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.CUSTOMER_ACCOUNT_NOT_EXISTS;

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
    @Resource
    private CustomerInfoService customerInfoService;
    @Resource
    private AccInfoConfigService accInfoConfigService;
    @Resource
    private DictDataApi dataApi;

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

    @Override
    public void accountAutoConstructor() {
        List<CustomerInfoDO> customerInfoS = customerInfoService.getCustomerInfoByNoAccount();
        List<AccInfoConfigDO> configs = accInfoConfigService.getAllAccInfoConfig();
        if (configs.isEmpty()) {
            return;
        }
        List<CustomerAccountDO> accounts = new ArrayList<>();
        for (CustomerInfoDO info : customerInfoS) {
            final String displayName = info.getDistributorName();
            for (AccInfoConfigDO config : configs) {
                CustomerAccountDO account = new CustomerAccountDO()
                        // 基本信息
                        .setDistributorId(info.getId())
                        // 动态生成字段
                        .setAccountId(info.getDistributorNum() + "-" + config.getId())
                        .setName(displayName)
                        .setCompany(config.getYwzt())
                        .setRemark(displayName + dataApi.getDictDataLabel("fx_business_entity", config.getYwzt()));

                accounts.add(account);
            }
        }
        // 分页批量插入
        int batchSize = 500;
        for (int i = 0; i < accounts.size(); i += batchSize) {
            List<CustomerAccountDO> subList = accounts.subList(i, Math.min(i + batchSize, accounts.size()));
            customerAccountMapper.insertBatch(subList);
        }
    }

}