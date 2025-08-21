package cn.iocoder.yudao.module.fx.service.billinginfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.billinginfo.vo.BillingInfoPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.billinginfo.vo.BillingInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.billinginfo.BillingInfoDO;
import cn.iocoder.yudao.module.fx.dal.mysql.billinginfo.BillingInfoMapper;
import cn.iocoder.yudao.module.fx.enums.BooleanType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.BILLING_INFO_NOT_EXISTS;

/**
 * 开票信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class BillingInfoServiceImpl implements BillingInfoService {

    @Resource
    private BillingInfoMapper billingInfoMapper;

    @Override
    public Integer createBillingInfo(BillingInfoSaveReqVO createReqVO) {
        // 插入
        BillingInfoDO billingInfo = BeanUtils.toBean(createReqVO, BillingInfoDO.class);
        billingInfoMapper.insert(billingInfo);
        // 返回
        return billingInfo.getId();
    }

    @Override
    public void updateBillingInfo(BillingInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateBillingInfoExists(updateReqVO.getId());
        // 更新
        BillingInfoDO updateObj = BeanUtils.toBean(updateReqVO, BillingInfoDO.class);
        billingInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteBillingInfo(Integer id) {
        // 校验存在
        validateBillingInfoExists(id);
        // 删除
        billingInfoMapper.deleteById(id);
    }

    private void validateBillingInfoExists(Integer id) {
        if (billingInfoMapper.selectById(id) == null) {
            throw exception(BILLING_INFO_NOT_EXISTS);
        }
    }

    @Override
    public BillingInfoDO getBillingInfo(Integer id) {
        return billingInfoMapper.selectById(id);
    }

    /**
     * 校验开票信息
     *
     * @param customerId 客商
     * @param company    购方名称
     * @param tax        纳税人识别号
     * @param bank       开户行及账号
     * @param address    地址及电话
     * @param email      发送邮箱
     * @return 开票信息是否存在
     */
    @Override
    public Boolean existsBillingInfo(Integer customerId, String company, String tax, String bank, String address, String email) {
        BillingInfoDO billingInfoDO = billingInfoMapper.selectOne(new LambdaQueryWrapper<BillingInfoDO>()
                .eq(BillingInfoDO::getCustomerId, customerId)
                .eq(BillingInfoDO::getCompany, company)
                .eq(BillingInfoDO::getTax, tax)
                .eq(BillingInfoDO::getBank, bank)
                .eq(BillingInfoDO::getAddress, address)
                .eq(BillingInfoDO::getEmail, email)
                .eq(BillingInfoDO::getIsActive, BooleanType.YES.getType()));
        return billingInfoDO != null;
    }

    @Override
    public PageResult<BillingInfoDO> getBillingInfoPage(BillingInfoPageReqVO pageReqVO) {
        return billingInfoMapper.selectPage(pageReqVO);
    }

}