package cn.iocoder.yudao.module.fx.service.customeraccount;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.amountadj.vo.AmountAdjSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.accinfoconfig.AccInfoConfigDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.module.fx.dal.mysql.customeraccount.CustomerAccountMapper;
import cn.iocoder.yudao.module.fx.enums.AmountAdjType;
import cn.iocoder.yudao.module.fx.enums.BooleanType;
import cn.iocoder.yudao.module.fx.service.accinfoconfig.AccInfoConfigService;
import cn.iocoder.yudao.module.fx.service.amountadj.AmountAdjService;
import cn.iocoder.yudao.module.fx.service.customerinfo.CustomerInfoService;
import cn.iocoder.yudao.module.fx.utils.BigDecimalUtils;
import cn.iocoder.yudao.module.system.api.dict.DictDataApi;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.diboot.core.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    private AmountAdjService amountAdjService;
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

    /**
     * 根据DO更新分销商账号
     *
     * @param account 更新信息
     */
    @Override
    public void updateCustomerAccountByDO(CustomerAccountDO account) {
        customerAccountMapper.updateById(account);
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

    /**
     * 根据分销商id和业务主体查询账号
     *
     * @param distributorId 分销商id
     * @param company       业务主体
     * @return
     */
    @Override
    public CustomerAccountDO getCustomerAccountByDistributorIdAndCompany(Long distributorId, Integer company) {
        return customerAccountMapper.selectOne(new LambdaQueryWrapper<CustomerAccountDO>()
                .eq(CustomerAccountDO::getDistributorId, distributorId)
                .eq(CustomerAccountDO::getCompany, company));
    }

    /**
     * 创建单个分销商的账户
     */
    @Override
    public void createSingleCustomerAccount(Long distributorId, Integer company, String distributorName) {
        customerAccountMapper.insert(new CustomerAccountDO()
                .setDistributorId(distributorId)
                .setCompany(company)
                .setAccountId(String.join(company.toString(), "-", distributorId.toString()))
                .setName(distributorName));
    }

    /**
     * 销售单发货后扣款并且自动生成账户调整记录【类型为扣款】
     *
     * @param orderInfo
     */
    @Override
    public BigDecimal saleReceivable(OrdersInfoDO orderInfo) {
        Long distributorId = orderInfo.getDistributorId();
        String orderId = orderInfo.getOrderId();
        CustomerAccountDO account = this.getCustomerAccountByDistributorIdAndCompany(distributorId, orderInfo.getReceiveSupplierId().intValue());
        if (account == null) {
            throw new BusinessException(StrUtil.format("分销商[{}]未找到分销商扣款账户", distributorId));
        }
        // 账户状态检查（冻结状态拦截）
        if (BooleanType.YES.getType().equals(account.getIsActive())) {
            throw new BusinessException(StrUtil.format("账户[{}]已冻结", account.getId()));
        }
        if (BigDecimalUtils.lt(account.getDetainAmount(), orderInfo.getSalesAmount())) {
            throw new BusinessException(StrUtil.format("{}:在单据扣款时存在异常，账户暂扣金额小于当前销售单金额，请确认！", orderId));
        }
        // 扣款
        account.setDetainAmount(BigDecimalUtils.subtract(account.getDetainAmount(), orderInfo.getSalesAmount()));
        customerAccountMapper.updateById(account);
        // 生成账户调整记录
        amountAdjService.createAmountAdj(new AmountAdjSaveReqVO()
                .setAmount(orderInfo.getSalesAmount())
                .setAccount(account.getId().toString())
                .setRemark(orderInfo.getRemark())
                .setSoId(orderId)
                .setOrderDate(DateUtil.now())
                .setType(AmountAdjType.SALE.getType())
                .setAdjustBalance(account.getBalance())
                .setAdjustWithholdBalance(account.getDetainAmount()));
        return account.getBalance();
    }

}