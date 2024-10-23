package cn.iocoder.yudao.module.fx.service.customeraccount;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;

import javax.validation.Valid;

/**
 * 分销商账号 Service 接口
 *
 * @author 管理员
 */
public interface CustomerAccountService {

    /**
     * 创建分销商账号
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomerAccount(@Valid CustomerAccountSaveReqVO createReqVO);

    /**
     * 更新分销商账号
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomerAccount(@Valid CustomerAccountSaveReqVO updateReqVO);

    /**
     * 删除分销商账号
     *
     * @param id 编号
     */
    void deleteCustomerAccount(Long id);

    /**
     * 获得分销商账号
     *
     * @param id 编号
     * @return 分销商账号
     */
    CustomerAccountDO getCustomerAccount(Long id);

    /**
     * 获得分销商账号分页
     *
     * @param pageReqVO 分页查询
     * @return 分销商账号分页
     */
    PageResult<CustomerAccountDO> getCustomerAccountPage(CustomerAccountPageReqVO pageReqVO);

}