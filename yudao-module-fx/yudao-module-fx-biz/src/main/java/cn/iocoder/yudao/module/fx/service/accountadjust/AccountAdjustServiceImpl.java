package cn.iocoder.yudao.module.fx.service.accountadjust;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.accountadjust.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.accountadjust.AccountAdjustDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.accountadjust.AccountAdjustMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 分销账户调整 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class AccountAdjustServiceImpl implements AccountAdjustService {

    @Resource
    private AccountAdjustMapper accountAdjustMapper;

    @Override
    public Integer createAccountAdjust(AccountAdjustSaveReqVO createReqVO) {
        // 插入
        AccountAdjustDO accountAdjust = BeanUtils.toBean(createReqVO, AccountAdjustDO.class);
        accountAdjustMapper.insert(accountAdjust);
        // 返回
        return accountAdjust.getId();
    }

    @Override
    public void updateAccountAdjust(AccountAdjustSaveReqVO updateReqVO) {
        // 校验存在
        validateAccountAdjustExists(updateReqVO.getId());
        // 更新
        AccountAdjustDO updateObj = BeanUtils.toBean(updateReqVO, AccountAdjustDO.class);
        accountAdjustMapper.updateById(updateObj);
    }

    @Override
    public void deleteAccountAdjust(Integer id) {
        // 校验存在
        validateAccountAdjustExists(id);
        // 删除
        accountAdjustMapper.deleteById(id);
    }

    private void validateAccountAdjustExists(Integer id) {
        if (accountAdjustMapper.selectById(id) == null) {
            throw exception(ACCOUNT_ADJUST_NOT_EXISTS);
        }
    }

    @Override
    public AccountAdjustDO getAccountAdjust(Integer id) {
        return accountAdjustMapper.selectById(id);
    }

    @Override
    public PageResult<AccountAdjustDO> getAccountAdjustPage(AccountAdjustPageReqVO pageReqVO) {
        return accountAdjustMapper.selectPage(pageReqVO);
    }

}