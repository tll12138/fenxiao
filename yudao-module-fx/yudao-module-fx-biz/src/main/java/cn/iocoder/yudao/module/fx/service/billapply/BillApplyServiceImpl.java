package cn.iocoder.yudao.module.fx.service.billapply;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplyPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplySaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.billapply.BillApplyDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.billapply.BillApplyDetailDO;
import cn.iocoder.yudao.module.fx.dal.mysql.billapply.BillApplyDetailMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.billapply.BillApplyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.BILL_APPLY_NOT_EXISTS;

/**
 * 发票申请 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class BillApplyServiceImpl implements BillApplyService {

    @Resource
    private BillApplyMapper billApplyMapper;
    @Resource
    private BillApplyDetailMapper billApplyDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createBillApply(BillApplySaveReqVO createReqVO) {
        // 插入
        BillApplyDO billApply = BeanUtils.toBean(createReqVO, BillApplyDO.class);
        billApplyMapper.insert(billApply);

        // 插入子表
        createBillApplyDetailList(billApply.getId(), createReqVO.getBillApplyDetails());
        // 返回
        return billApply.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBillApply(BillApplySaveReqVO updateReqVO) {
        // 校验存在
        validateBillApplyExists(updateReqVO.getId());
        // 更新
        BillApplyDO updateObj = BeanUtils.toBean(updateReqVO, BillApplyDO.class);
        billApplyMapper.updateById(updateObj);

        // 更新子表
        updateBillApplyDetailList(updateReqVO.getId(), updateReqVO.getBillApplyDetails());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBillApply(Integer id) {
        // 校验存在
        validateBillApplyExists(id);
        // 删除
        billApplyMapper.deleteById(id);

        // 删除子表
        deleteBillApplyDetailByMainId(id);
    }

    private void validateBillApplyExists(Integer id) {
        if (billApplyMapper.selectById(id) == null) {
            throw exception(BILL_APPLY_NOT_EXISTS);
        }
    }

    @Override
    public BillApplyDO getBillApply(Integer id) {
        return billApplyMapper.selectById(id);
    }

    @Override
    public PageResult<BillApplyDO> getBillApplyPage(BillApplyPageReqVO pageReqVO) {
        return billApplyMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（发票申请详情） ====================

    @Override
    public List<BillApplyDetailDO> getBillApplyDetailListByMainId(Integer mainId) {
        return billApplyDetailMapper.selectListByMainId(mainId);
    }

    @Override
    public Integer saveBillApply(BillApplySaveReqVO createReqVO) {
        // 插入
        BillApplyDO billApply = BeanUtils.toBean(createReqVO, BillApplyDO.class);
        billApplyMapper.insert(billApply);

        // 插入子表
        createBillApplyDetailList(billApply.getId(), createReqVO.getBillApplyDetails());
        // 返回
        return billApply.getId();
    }

    private void createBillApplyDetailList(Integer mainId, List<BillApplyDetailDO> list) {
        list.forEach(o -> o.setMainId(mainId));
        billApplyDetailMapper.insertBatch(list);
    }

    private void updateBillApplyDetailList(Integer mainId, List<BillApplyDetailDO> list) {
        deleteBillApplyDetailByMainId(mainId);
        list.forEach(o -> o.setId(null).setUpdater(null).setUpdateTime(null)); // 解决更新情况下：1）id 冲突；2）updateTime 不更新
        createBillApplyDetailList(mainId, list);
    }

    private void deleteBillApplyDetailByMainId(Integer mainId) {
        billApplyDetailMapper.deleteByMainId(mainId);
    }

}