package cn.iocoder.yudao.module.fx.service.carcptaud;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.carcptaud.vo.CaRcptAudPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.carcptaud.vo.CaRcptAudSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.carcptaud.CaRcptAudDO;

import javax.validation.Valid;

/**
 * 客商账户收款审核 Service 接口
 *
 * @author 管理员
 */
public interface CaRcptAudService {

    /**
     * 创建客商账户收款审核
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createCaRcptAud(@Valid CaRcptAudSaveReqVO createReqVO);

    /**
     * 更新客商账户收款审核
     *
     * @param updateReqVO 更新信息
     */
    void updateCaRcptAud(@Valid CaRcptAudSaveReqVO updateReqVO);

    /**
     * 删除客商账户收款审核
     *
     * @param id 编号
     */
    void deleteCaRcptAud(Integer id);

    /**
     * 获得客商账户收款审核
     *
     * @param id 编号
     * @return 客商账户收款审核
     */
    CaRcptAudDO getCaRcptAud(Integer id);

    /**
     * 获得客商账户收款审核
     *
     * @param processInstanceId 编号
     * @return 客商账户收款审核
     */
    CaRcptAudDO getByProcessInstanceId(String processInstanceId);

    /**
     * 获得客商账户收款审核分页
     *
     * @param pageReqVO 分页查询
     * @return 客商账户收款审核分页
     */
    PageResult<CaRcptAudDO> getCaRcptAudPage(CaRcptAudPageReqVO pageReqVO);

    /**
     * 用户创建流程实例
     */
    void startProcessInstance(Long loginUserId, CaRcptAudSaveReqVO createReqVO);

    /**
     * 根据流程号获取审批单信息
     */
    CaRcptAudDO getInfoByPIId(String processInstanceId);

    /**
     * 校验打款账户是否存在，不存在新增，存在就更新打款账号累计次数与金额
     */
    void updateOrInsertPayAcc(CaRcptAudDO caRcptAudDO);
}