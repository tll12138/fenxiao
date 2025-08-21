package cn.iocoder.yudao.module.fx.service.billinginfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.billinginfo.vo.BillingInfoPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.billinginfo.vo.BillingInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.billinginfo.BillingInfoDO;

import javax.validation.Valid;

/**
 * 开票信息 Service 接口
 *
 * @author 管理员
 */
public interface BillingInfoService {

    /**
     * 创建开票信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createBillingInfo(@Valid BillingInfoSaveReqVO createReqVO);

    /**
     * 更新开票信息
     *
     * @param updateReqVO 更新信息
     */
    void updateBillingInfo(@Valid BillingInfoSaveReqVO updateReqVO);

    /**
     * 删除开票信息
     *
     * @param id 编号
     */
    void deleteBillingInfo(Integer id);

    /**
     * 获得开票信息
     *
     * @param id 编号
     * @return 开票信息
     */
    BillingInfoDO getBillingInfo(Integer id);

    /**
     * 校验开票信息
     *
     * @param customerId 客商
     * @param company    购方名称
     * @param tax        纳税人识别号
     * @param bank       开户行及账号
     * @param address    地址及电话
     * @param email      发送邮箱
     * @return 开票信息是否存在
     */
    Boolean existsBillingInfo(Integer customerId, String company, String tax, String bank, String address, String email);

    /**
     * 获得开票信息分页
     *
     * @param pageReqVO 分页查询
     * @return 开票信息分页
     */
    PageResult<BillingInfoDO> getBillingInfoPage(BillingInfoPageReqVO pageReqVO);

}