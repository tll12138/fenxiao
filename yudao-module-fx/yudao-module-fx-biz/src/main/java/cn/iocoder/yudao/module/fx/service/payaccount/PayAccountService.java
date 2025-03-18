package cn.iocoder.yudao.module.fx.service.payaccount;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.fx.controller.admin.payaccount.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.payaccount.PayAccountDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 分销支付账户 Service 接口
 *
 * @author 管理员
 */
public interface PayAccountService {

    /**
     * 创建分销支付账户
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createPayAccount(@Valid PayAccountSaveReqVO createReqVO);

    /**
     * 更新分销支付账户
     *
     * @param updateReqVO 更新信息
     */
    void updatePayAccount(@Valid PayAccountSaveReqVO updateReqVO);

    /**
     * 删除分销支付账户
     *
     * @param id 编号
     */
    void deletePayAccount(Integer id);

    /**
     * 获得分销支付账户
     *
     * @param id 编号
     * @return 分销支付账户
     */
    PayAccountDO getPayAccount(Integer id);

    /**
     * 获得分销支付账户分页
     *
     * @param pageReqVO 分页查询
     * @return 分销支付账户分页
     */
    PageResult<PayAccountDO> getPayAccountPage(PayAccountPageReqVO pageReqVO);

}