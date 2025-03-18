package cn.iocoder.yudao.module.fx.service.emailaddress;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.emailaddress.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.emailaddress.EmailAddressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.emailaddress.EmailAddressMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 发票邮箱库 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class EmailAddressServiceImpl implements EmailAddressService {

    @Resource
    private EmailAddressMapper emailAddressMapper;

    @Override
    public Integer createEmailAddress(EmailAddressSaveReqVO createReqVO) {
        // 插入
        EmailAddressDO emailAddress = BeanUtils.toBean(createReqVO, EmailAddressDO.class);
        emailAddressMapper.insert(emailAddress);
        // 返回
        return emailAddress.getId();
    }

    @Override
    public void updateEmailAddress(EmailAddressSaveReqVO updateReqVO) {
        // 校验存在
        validateEmailAddressExists(updateReqVO.getId());
        // 更新
        EmailAddressDO updateObj = BeanUtils.toBean(updateReqVO, EmailAddressDO.class);
        emailAddressMapper.updateById(updateObj);
    }

    @Override
    public void deleteEmailAddress(Integer id) {
        // 校验存在
        validateEmailAddressExists(id);
        // 删除
        emailAddressMapper.deleteById(id);
    }

    private void validateEmailAddressExists(Integer id) {
        if (emailAddressMapper.selectById(id) == null) {
            throw exception(EMAIL_ADDRESS_NOT_EXISTS);
        }
    }

    @Override
    public EmailAddressDO getEmailAddress(Integer id) {
        return emailAddressMapper.selectById(id);
    }

    @Override
    public PageResult<EmailAddressDO> getEmailAddressPage(EmailAddressPageReqVO pageReqVO) {
        return emailAddressMapper.selectPage(pageReqVO);
    }

}