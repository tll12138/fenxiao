package cn.iocoder.yudao.module.fx.service.billapply;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplyPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplySaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.billapply.BillApplyDO;

import javax.validation.Valid;

/**
 * 发票申请 Service 接口
 *
 * @author 管理员
 */
public interface BillApplyService {

    /**
     * 创建发票申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createBillApply(@Valid BillApplySaveReqVO createReqVO);

    /**
     * 更新发票申请
     *
     * @param updateReqVO 更新信息
     */
    void updateBillApply(@Valid BillApplySaveReqVO updateReqVO);

    /**
     * 删除发票申请
     *
     * @param id 编号
     */
    void deleteBillApply(Integer id);

    /**
     * 获得发票申请
     *
     * @param id 编号
     * @return 发票申请
     */
    BillApplyDO getBillApply(Integer id);

    /**
     * 获得发票申请分页
     *
     * @param pageReqVO 分页查询
     * @return 发票申请分页
     */
    PageResult<BillApplyDO> getBillApplyPage(BillApplyPageReqVO pageReqVO);
}