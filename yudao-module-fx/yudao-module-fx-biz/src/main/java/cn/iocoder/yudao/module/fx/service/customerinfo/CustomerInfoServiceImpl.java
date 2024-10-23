package cn.iocoder.yudao.module.fx.service.customerinfo;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.customeraddress.vo.CustomerAddressDetailRespVO;
import cn.iocoder.yudao.module.fx.convert.CustomerCovert;
import cn.iocoder.yudao.module.fx.dal.dataobject.subcompanyinfo.SubCompanyInfoDO;
import cn.iocoder.yudao.module.fx.dal.mysql.subcompanyinfo.SubCompanyInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diboot.core.binding.Binder;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.customerinfo.CustomerInfoMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.customeraccount.CustomerAccountMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.customeraddress.CustomerAddressMapper;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 分销商基础信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class CustomerInfoServiceImpl implements CustomerInfoService {

    @Resource
    private CustomerInfoMapper customerInfoMapper;
    @Resource
    private CustomerAccountMapper customerAccountMapper;
    @Resource
    private CustomerAddressMapper customerAddressMapper;

    @Resource
    private SubCompanyInfoMapper companyInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCustomerInfo(CustomerInfoSaveReqVO createReqVO) {
        // 插入分销商基础信息
        CustomerInfoDO customerInfo = CustomerCovert.INSTANCE.convert(createReqVO);
        customerInfoMapper.insert(customerInfo);

        // 插入地址信息
        createCustomerAddressList(customerInfo.getId(), createReqVO.getCustomerAddressList());

        // 插入账号信息
        createCustomerAccountList(customerInfo);

        // 返回
        return customerInfo.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCustomerInfo(CustomerInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerInfoExists(updateReqVO.getId());

        // 更新
        CustomerInfoDO updateObj = BeanUtils.toBean(updateReqVO, CustomerInfoDO.class);
        customerInfoMapper.updateById(updateObj);

        // 更新子表
        updateCustomerAddressList(updateReqVO.getId(), updateReqVO.getCustomerAddressList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCustomerInfo(Long id) {
        // 校验存在
        validateCustomerInfoExists(id);
        // 删除
        customerInfoMapper.deleteById(id);

        // 删除子表
        deleteCustomerAccountById(id);
    }

    private void validateCustomerInfoExists(Long id) {
        if (customerInfoMapper.selectById(id) == null) {
            throw exception(CUSTOMER_INFO_NOT_EXISTS);
        }
    }

    @Override
    public CustomerInfoDO getCustomerInfo(Long id) {
        return customerInfoMapper.selectById(id);
    }

    @Override
    public CustomerInfoDetailRespVO getCustomerInfoDetail(Long id) {
        //获取分销商基础信息
        CustomerInfoDO customerInfoDO = customerInfoMapper.selectById(id);

        //将基础信息转换为详细返回信息对象
        CustomerInfoDetailRespVO customerInfoDetailRespVO =
                CustomerCovert.INSTANCE.convert2Detail(customerInfoDO);

        //获取分销商地址信息
        LambdaQueryWrapper<CustomerAddressDO> addressQueryWrapper = new LambdaQueryWrapper<>();
        addressQueryWrapper.eq(CustomerAddressDO::getDistributorId,id);
        List<CustomerAddressDO> customerAddressDOS = customerAddressMapper.selectList(addressQueryWrapper);
        customerInfoDetailRespVO.setCustomerAddressList(CustomerCovert.INSTANCE.convert2AddressDetailList(customerAddressDOS));

        //获取分销商账号信息
        LambdaQueryWrapper<CustomerAccountDO> accountQueryWrapper = new LambdaQueryWrapper<>();
        accountQueryWrapper.eq(CustomerAccountDO::getDistributorId,id);
        List<CustomerAccountDO> accountDOList = customerAccountMapper.selectList(accountQueryWrapper);
        List<CustomerAccountRespVO> customerAccountRespVOS = CustomerCovert.INSTANCE.convertAccount(accountDOList);
        customerInfoDetailRespVO.setCustomerAccounts(customerAccountRespVOS);
        Binder.bindRelations(customerAccountRespVOS);

        return customerInfoDetailRespVO;
    }

    @Override
    public PageResult<CustomerInfoDO> getCustomerInfoPage(CustomerInfoPageReqVO pageReqVO) {
        return customerInfoMapper.selectPage(pageReqVO);
    }
    @Override
    public PageResult<CustomerInfoDetailPageRespVO> getCustomerInfoDetailPage(CustomerInfoPageReqVO pageReqVO) {
        PageResult<CustomerInfoDO> customerInfoDOPageResult = customerInfoMapper.selectPage(pageReqVO);
        List<CustomerInfoDetailPageRespVO> customerInfoDetailRespVOS =
                Binder.convertAndBindRelations(customerInfoDOPageResult.getList(), CustomerInfoDetailPageRespVO.class);
        return new PageResult<>(customerInfoDetailRespVOS, customerInfoDOPageResult.getTotal());
    }


    // ==================== 子表（分销商账号） ====================

    @Override
    public List<CustomerAccountDO> getCustomerAccountListById(Long id) {
        return customerAccountMapper.selectListById(id);
    }

    private void createCustomerAccountList(CustomerInfoDO customerInfoDO) {
        LambdaQueryWrapper<SubCompanyInfoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubCompanyInfoDO::getIsInitCompany,1);
        List<SubCompanyInfoDO> subCompanyInfoDOS = companyInfoMapper.selectList(queryWrapper);
        List<CustomerAccountDO> accountDOList = subCompanyInfoDOS.stream().map((item) -> {
            CustomerAccountDO customerAccountDO = new CustomerAccountDO();
            customerAccountDO.setDistributorId(customerInfoDO.getId());//设置分销商ID为已插入的基础信息返回的ID
            customerAccountDO.setCompany(item.getId());
            customerAccountDO.setBalance(new BigDecimal(0));
            int index = subCompanyInfoDOS.indexOf(item);
            customerAccountDO.setAccountId(customerInfoDO.getDistributorId() + "-" + index);
            return customerAccountDO;
        }).collect(Collectors.toList());

        Boolean b = customerAccountMapper.insertBatch(accountDOList);
        if (!b){
            throw exception(CUSTOMER_ACCOUNT_CREATE_FAIL);
        }
    }


    private void deleteCustomerAccountById(Long id) {
        customerAccountMapper.delete(
                new LambdaQueryWrapper<CustomerAccountDO>()
                        .eq(CustomerAccountDO::getDistributorId, id)
        );
    }

    // ==================== 子表（分销商地址） ====================

    @Override
    public List<CustomerAddressDO> getCustomerAddressListById(Long id) {
        return customerAddressMapper.selectListById(id);
    }

    private void createCustomerAddressList(Long id, List<CustomerAddressDO> list) {
        list.forEach(o -> o.setDistributorId(id));
        customerAddressMapper.insertBatch(list);
    }

    private void updateCustomerAddressList(Long id, List<CustomerAddressDO> list) {
        if (CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(o -> o.setDistributorId(id));
        Boolean b = customerAddressMapper.insertOrUpdateBatch(list);
        if (!b){
            throw exception(CUSTOMER_ADDRESS_UPDATE_FAIL);
        }
    }

}