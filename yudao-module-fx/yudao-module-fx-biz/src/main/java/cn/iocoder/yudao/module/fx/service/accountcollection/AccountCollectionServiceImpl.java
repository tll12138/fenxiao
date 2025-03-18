package cn.iocoder.yudao.module.fx.service.accountcollection;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.accountcollection.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.accountcollection.AccountCollectionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.accountcollection.AccountCollectionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 分销账户收款记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class AccountCollectionServiceImpl implements AccountCollectionService {

    @Resource
    private AccountCollectionMapper accountCollectionMapper;

    @Override
    public Integer createAccountCollection(AccountCollectionSaveReqVO createReqVO) {
        // 插入
        AccountCollectionDO accountCollection = BeanUtils.toBean(createReqVO, AccountCollectionDO.class);
        accountCollectionMapper.insert(accountCollection);
        // 返回
        return accountCollection.getId();
    }

    @Override
    public void updateAccountCollection(AccountCollectionSaveReqVO updateReqVO) {
        // 校验存在
        validateAccountCollectionExists(updateReqVO.getId());
        // 更新
        AccountCollectionDO updateObj = BeanUtils.toBean(updateReqVO, AccountCollectionDO.class);
        accountCollectionMapper.updateById(updateObj);
    }

    @Override
    public void deleteAccountCollection(Integer id) {
        // 校验存在
        validateAccountCollectionExists(id);
        // 删除
        accountCollectionMapper.deleteById(id);
    }

    private void validateAccountCollectionExists(Integer id) {
        if (accountCollectionMapper.selectById(id) == null) {
            throw exception(ACCOUNT_COLLECTION_NOT_EXISTS);
        }
    }

    @Override
    public AccountCollectionDO getAccountCollection(Integer id) {
        return accountCollectionMapper.selectById(id);
    }

    @Override
    public PageResult<AccountCollectionDO> getAccountCollectionPage(AccountCollectionPageReqVO pageReqVO) {
        return accountCollectionMapper.selectPage(pageReqVO);
    }

}