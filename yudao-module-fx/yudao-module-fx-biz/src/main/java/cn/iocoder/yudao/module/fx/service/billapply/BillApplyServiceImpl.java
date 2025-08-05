package cn.iocoder.yudao.module.fx.service.billapply;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplyPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplySaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.billapply.BillApplyDO;
import cn.iocoder.yudao.module.fx.dal.mysql.billapply.BillApplyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createBillApply(BillApplySaveReqVO createReqVO) {
        // 插入
        BillApplyDO billApply = BeanUtils.toBean(createReqVO, BillApplyDO.class);
        billApplyMapper.insert(billApply);
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
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBillApply(Integer id) {
        // 校验存在
        validateBillApplyExists(id);
        // 删除
        billApplyMapper.deleteById(id);
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

}