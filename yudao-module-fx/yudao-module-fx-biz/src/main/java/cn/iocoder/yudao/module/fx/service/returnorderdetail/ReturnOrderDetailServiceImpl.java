package cn.iocoder.yudao.module.fx.service.returnorderdetail;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.returnorderdetail.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorderdetail.ReturnOrderDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.returnorderdetail.ReturnOrderDetailMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 销售退货详情 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ReturnOrderDetailServiceImpl implements ReturnOrderDetailService {

    @Resource
    private ReturnOrderDetailMapper returnOrderDetailMapper;

    @Override
    public Long createReturnOrderDetail(ReturnOrderDetailSaveReqVO createReqVO) {
        // 插入
        ReturnOrderDetailDO returnOrderDetail = BeanUtils.toBean(createReqVO, ReturnOrderDetailDO.class);
        returnOrderDetailMapper.insert(returnOrderDetail);
        // 返回
        return returnOrderDetail.getId();
    }

    @Override
    public void updateReturnOrderDetail(ReturnOrderDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateReturnOrderDetailExists(updateReqVO.getId());
        // 更新
        ReturnOrderDetailDO updateObj = BeanUtils.toBean(updateReqVO, ReturnOrderDetailDO.class);
        returnOrderDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteReturnOrderDetail(Long id) {
        // 校验存在
        validateReturnOrderDetailExists(id);
        // 删除
        returnOrderDetailMapper.deleteById(id);
    }

    private void validateReturnOrderDetailExists(Long id) {
        if (returnOrderDetailMapper.selectById(id) == null) {
            throw exception(RETURN_ORDER_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public ReturnOrderDetailDO getReturnOrderDetail(Long id) {
        return returnOrderDetailMapper.selectById(id);
    }

    @Override
    public PageResult<ReturnOrderDetailDO> getReturnOrderDetailPage(ReturnOrderDetailPageReqVO pageReqVO) {
        return returnOrderDetailMapper.selectPage(pageReqVO);
    }

}