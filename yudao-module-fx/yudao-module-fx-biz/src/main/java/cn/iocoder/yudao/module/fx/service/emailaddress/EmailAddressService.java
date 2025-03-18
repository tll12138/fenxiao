package cn.iocoder.yudao.module.fx.service.emailaddress;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.fx.controller.admin.emailaddress.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.emailaddress.EmailAddressDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 发票邮箱库 Service 接口
 *
 * @author 管理员
 */
public interface EmailAddressService {

    /**
     * 创建发票邮箱库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createEmailAddress(@Valid EmailAddressSaveReqVO createReqVO);

    /**
     * 更新发票邮箱库
     *
     * @param updateReqVO 更新信息
     */
    void updateEmailAddress(@Valid EmailAddressSaveReqVO updateReqVO);

    /**
     * 删除发票邮箱库
     *
     * @param id 编号
     */
    void deleteEmailAddress(Integer id);

    /**
     * 获得发票邮箱库
     *
     * @param id 编号
     * @return 发票邮箱库
     */
    EmailAddressDO getEmailAddress(Integer id);

    /**
     * 获得发票邮箱库分页
     *
     * @param pageReqVO 分页查询
     * @return 发票邮箱库分页
     */
    PageResult<EmailAddressDO> getEmailAddressPage(EmailAddressPageReqVO pageReqVO);

}