package cn.iocoder.yudao.module.fx.service.accountadjust;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.fx.controller.admin.accountadjust.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.accountadjust.AccountAdjustDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 分销账户调整 Service 接口
 *
 * @author 管理员
 */
public interface AccountAdjustService {

    /**
     * 创建分销账户调整
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createAccountAdjust(@Valid AccountAdjustSaveReqVO createReqVO);

    /**
     * 更新分销账户调整
     *
     * @param updateReqVO 更新信息
     */
    void updateAccountAdjust(@Valid AccountAdjustSaveReqVO updateReqVO);

    /**
     * 删除分销账户调整
     *
     * @param id 编号
     */
    void deleteAccountAdjust(Integer id);

    /**
     * 获得分销账户调整
     *
     * @param id 编号
     * @return 分销账户调整
     */
    AccountAdjustDO getAccountAdjust(Integer id);

    /**
     * 获得分销账户调整分页
     *
     * @param pageReqVO 分页查询
     * @return 分销账户调整分页
     */
    PageResult<AccountAdjustDO> getAccountAdjustPage(AccountAdjustPageReqVO pageReqVO);

}