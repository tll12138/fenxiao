package cn.iocoder.yudao.module.fx.service.amountadj;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.amountadj.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.amountadj.AmountAdjDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.amountadj.AmountAdjMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 分销账户资金调整记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class AmountAdjServiceImpl implements AmountAdjService {

    @Resource
    private AmountAdjMapper amountAdjMapper;

    @Override
    public Integer createAmountAdj(AmountAdjSaveReqVO createReqVO) {
        // 插入
        AmountAdjDO amountAdj = BeanUtils.toBean(createReqVO, AmountAdjDO.class);
        amountAdjMapper.insert(amountAdj);
        // 返回
        return amountAdj.getId();
    }

    @Override
    public void updateAmountAdj(AmountAdjSaveReqVO updateReqVO) {
        // 校验存在
        validateAmountAdjExists(updateReqVO.getId());
        // 更新
        AmountAdjDO updateObj = BeanUtils.toBean(updateReqVO, AmountAdjDO.class);
        amountAdjMapper.updateById(updateObj);
    }

    @Override
    public void deleteAmountAdj(Integer id) {
        // 校验存在
        validateAmountAdjExists(id);
        // 删除
        amountAdjMapper.deleteById(id);
    }

    private void validateAmountAdjExists(Integer id) {
        if (amountAdjMapper.selectById(id) == null) {
            throw exception(AMOUNT_ADJ_NOT_EXISTS);
        }
    }

    @Override
    public AmountAdjDO getAmountAdj(Integer id) {
        return amountAdjMapper.selectById(id);
    }

    @Override
    public PageResult<AmountAdjDO> getAmountAdjPage(AmountAdjPageReqVO pageReqVO) {
        return amountAdjMapper.selectPage(pageReqVO);
    }

}