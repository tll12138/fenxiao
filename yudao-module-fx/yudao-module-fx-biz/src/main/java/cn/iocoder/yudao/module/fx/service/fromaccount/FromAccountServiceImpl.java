package cn.iocoder.yudao.module.fx.service.fromaccount;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.fromaccount.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.fromaccount.FromAccountDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.fromaccount.FromAccountMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 *  分销打款账户 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class FromAccountServiceImpl implements FromAccountService {

    @Resource
    private FromAccountMapper fromAccountMapper;

    @Override
    public Integer createFromAccount(FromAccountSaveReqVO createReqVO) {
        // 插入
        FromAccountDO fromAccount = BeanUtils.toBean(createReqVO, FromAccountDO.class);
        fromAccountMapper.insert(fromAccount);
        // 返回
        return fromAccount.getId();
    }

    @Override
    public void updateFromAccount(FromAccountSaveReqVO updateReqVO) {
        // 校验存在
        validateFromAccountExists(updateReqVO.getId());
        // 更新
        FromAccountDO updateObj = BeanUtils.toBean(updateReqVO, FromAccountDO.class);
        fromAccountMapper.updateById(updateObj);
    }

    @Override
    public void deleteFromAccount(Integer id) {
        // 校验存在
        validateFromAccountExists(id);
        // 删除
        fromAccountMapper.deleteById(id);
    }

    private void validateFromAccountExists(Integer id) {
        if (fromAccountMapper.selectById(id) == null) {
            throw exception(FROM_ACCOUNT_NOT_EXISTS);
        }
    }

    @Override
    public FromAccountDO getFromAccount(Integer id) {
        return fromAccountMapper.selectById(id);
    }

    @Override
    public PageResult<FromAccountDO> getFromAccountPage(FromAccountPageReqVO pageReqVO) {
        return fromAccountMapper.selectPage(pageReqVO);
    }

}