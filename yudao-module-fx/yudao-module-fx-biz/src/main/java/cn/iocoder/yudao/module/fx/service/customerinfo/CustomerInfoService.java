package cn.iocoder.yudao.module.fx.service.customerinfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoDetailPageRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoSyncVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 分销商基础信息 Service 接口
 *
 * @author 管理员
 */
public interface CustomerInfoService {

    /**
     * 创建分销商基础信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomerInfo(@Valid CustomerInfoSaveReqVO createReqVO);

    /**
     * 更新分销商基础信息
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomerInfo(@Valid CustomerInfoSaveReqVO updateReqVO);

    /**
     * 删除分销商基础信息
     *
     * @param id 编号
     */
    void deleteCustomerInfo(Long id);

    /**
     * 获得分销商基础信息
     *
     * @param id 编号
     * @return 分销商基础信息
     */
    CustomerInfoDO getCustomerInfo(Long id);

    /**
     * 获得分销商基础信息
     *
     * @param customerName 分销商名称
     * @return 分销商基础信息
     */
    CustomerInfoDO getCustomerInfoByName(String customerName);

    /**
     * 获得分销商详细基础信息
     *
     * @param id 编号
     * @return 分销商基础信息
     */
    CustomerInfoDetailRespVO getCustomerInfoDetail(Long id);

    /**
     * 获得全量分销商详细基础信息
     *
     * @return 分销商基础信息
     */
    List<CustomerInfoDO> getAllCustomerInfo();

    /**
     * 获得分销商基础信息分页
     *
     * @param pageReqVO 分页查询
     * @return 分销商基础信息分页
     */
    PageResult<CustomerInfoDO> getCustomerInfoPage(CustomerInfoPageReqVO pageReqVO);

    /**
     * 获得分销商基础信息分页
     *
     * @param pageReqVO 分页查询
     * @return 分销商基础信息分页
     */
    PageResult<CustomerInfoDetailPageRespVO> getCustomerInfoDetailPage(CustomerInfoPageReqVO pageReqVO);

    /**
     * 同步分销客商信息
     */
    void syncCustomers();

    // ==================== 子表（分销商账号） ====================

    /**
     * 获得分销商账号列表
     *
     * @param id ID
     * @return 分销商账号列表
     */
    List<CustomerAccountDO> getCustomerAccountListById(Long id);

    // ==================== 子表（分销商地址） ====================

    /**
     * 获得分销商地址列表
     *
     * @param id ID
     * @return 分销商地址列表
     */
    List<CustomerAddressDO> getCustomerAddressListById(Long id);

    /**
     * 获得无账号分销商地址列表
     */
    List<CustomerInfoDO> getCustomerInfoByNoAccount();

    /**
     * 同步OA客商信息
     */
    void syncOaCustomers(List<CustomerInfoSyncVO> syncVO);
}