package cn.iocoder.yudao.module.fx.service.bigcustomeraddress;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.bigcustomeraddress.vo.BigCustomerAddressPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.bigcustomeraddress.vo.BigCustomerAddressSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.bigcustomeraddress.BigCustomerAddressDO;
import cn.iocoder.yudao.module.fx.dal.mysql.bigcustomeraddress.BigCustomerAddressMapper;
import cn.iocoder.yudao.module.fx.utils.BigDecimalUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.BIG_CUSTOMER_ADDRESS_NOT_EXISTS;

/**
 * 分销大客户地址 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class BigCustomerAddressServiceImpl implements BigCustomerAddressService {

    @Resource
    private BigCustomerAddressMapper bigCustomerAddressMapper;

    @Override
    public Long createBigCustomerAddress(BigCustomerAddressSaveReqVO createReqVO) {
        // 插入
        BigCustomerAddressDO bigCustomerAddress = BeanUtils.toBean(createReqVO, BigCustomerAddressDO.class);
        bigCustomerAddressMapper.insert(bigCustomerAddress);
        // 返回
        return bigCustomerAddress.getId();
    }

    @Override
    public void updateBigCustomerAddress(BigCustomerAddressSaveReqVO updateReqVO) {
        // 校验存在
        validateBigCustomerAddressExists(updateReqVO.getId());
        // 更新
        BigCustomerAddressDO updateObj = BeanUtils.toBean(updateReqVO, BigCustomerAddressDO.class);
        bigCustomerAddressMapper.updateById(updateObj);
    }

    /**
     * 更新分销大客户地址数量
     *
     * @param id
     */
    @Override
    public void updateBigCustomerAddressCountById(Long id) {
        // 校验存在
        BigCustomerAddressDO addressDO = validateBigCustomerAddressExists(id);
        addressDO.setCount(BigDecimalUtils.add(addressDO.getCount(), BigDecimal.ONE));
        bigCustomerAddressMapper.updateById(addressDO);
    }

    @Override
    public void deleteBigCustomerAddress(Long id) {
        // 校验存在
        validateBigCustomerAddressExists(id);
        // 删除
        bigCustomerAddressMapper.deleteById(id);
    }

    private BigCustomerAddressDO validateBigCustomerAddressExists(Long id) {
        BigCustomerAddressDO addressDO = bigCustomerAddressMapper.selectById(id);
        if (addressDO == null) {
            throw exception(BIG_CUSTOMER_ADDRESS_NOT_EXISTS);
        }
        return addressDO;
    }

    @Override
    public BigCustomerAddressDO getBigCustomerAddress(Long id) {
        return bigCustomerAddressMapper.selectById(id);
    }

    @Override
    public PageResult<BigCustomerAddressDO> getBigCustomerAddressPage(BigCustomerAddressPageReqVO pageReqVO) {
        return bigCustomerAddressMapper.selectPage(pageReqVO);
    }

}