package cn.iocoder.yudao.module.fx.service.customeraccount;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;

import javax.validation.Valid;
import java.math.BigDecimal;

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
     * 根据DO更新分销商账号
     *
     * @param account 更新信息
     */
    void updateCustomerAccountByDO(CustomerAccountDO account);

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

    /**
     * 自动分销商账号配置
     */
    void accountAutoConstructor();

    /**
     * 根据分销商id和业务主体查询账号
     *
     * @param distributorId 分销商id
     * @param company       业务主体
     * @return
     */
    CustomerAccountDO getCustomerAccountByDistributorIdAndCompany(Long distributorId, Integer company);

    /**
     * 创建单个分销商的账户
     */
    void createSingleCustomerAccount(Long distributorId, Integer company, String distributorName);

    /**
     * 销售单发货后扣款并且自动生成账户调整记录【类型为扣款】
     */
    BigDecimal saleReceivable(OrdersInfoDO orderInfo);

    /**
     * 销售退货提交后自动生成退货还款，并且自动生成账户调整记录【类型为退货还款】
     */
    BigDecimal resaleReceivable(ReturnOrdersInfoDetailRespVO returnOrder);


}