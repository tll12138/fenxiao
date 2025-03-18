package cn.iocoder.yudao.module.fx.service.payaccount;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.payaccount.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.payaccount.PayAccountDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.payaccount.PayAccountMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 分销支付账户 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PayAccountServiceImpl implements PayAccountService {

    @Resource
    private PayAccountMapper payAccountMapper;

    @Override
    public Integer createPayAccount(PayAccountSaveReqVO createReqVO) {
        // 插入
        PayAccountDO payAccount = BeanUtils.toBean(createReqVO, PayAccountDO.class);
        payAccountMapper.insert(payAccount);
        // 返回
        return payAccount.getId();
    }

    @Override
    public void updatePayAccount(PayAccountSaveReqVO updateReqVO) {
        // 校验存在
        validatePayAccountExists(updateReqVO.getId());
        // 更新
        PayAccountDO updateObj = BeanUtils.toBean(updateReqVO, PayAccountDO.class);
        payAccountMapper.updateById(updateObj);
    }

    @Override
    public void deletePayAccount(Integer id) {
        // 校验存在
        validatePayAccountExists(id);
        // 删除
        payAccountMapper.deleteById(id);
    }

    private void validatePayAccountExists(Integer id) {
        if (payAccountMapper.selectById(id) == null) {
            throw exception(PAY_ACCOUNT_NOT_EXISTS);
        }
    }

    @Override
    public PayAccountDO getPayAccount(Integer id) {
        return payAccountMapper.selectById(id);
    }

    @Override
    public PageResult<PayAccountDO> getPayAccountPage(PayAccountPageReqVO pageReqVO) {
        return payAccountMapper.selectPage(pageReqVO);
    }

}