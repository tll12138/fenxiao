package cn.iocoder.yudao.module.fx.service.returnorder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrderPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrderSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorder.ReturnOrderDO;
import cn.iocoder.yudao.module.fx.dal.mysql.ordersdetail.OrdersDetailMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.returnorder.ReturnOrderMapper;
import cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.fx.utils.returnorder.ReturnOrderProcessingContext;
import cn.iocoder.yudao.module.fx.utils.returnorder.template.SaveReturnOrderProcessing;
import cn.iocoder.yudao.module.fx.utils.returnorder.template.SubmitReturnOrderProcessing;
import cn.iocoder.yudao.module.fx.utils.template.TemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.RETURN_ORDER_NOT_EXISTS;

/**
 * FX 销售退货单 Service 实现类
 *
 * @author 员工
 */
@Service
@Validated
@Slf4j
public class ReturnOrderServiceImpl implements ReturnOrderService {

    @Resource
    private ReturnOrderMapper returnOrderMapper;

    @Resource
    private OrdersDetailMapper ordersDetailMapper;


    @Override
    public Long saveOrdersInfo(ReturnOrderSaveReqVO saveReqVO) {

        if (saveReqVO == null){
            throw exception(ErrorCodeConstants.ORDERS_INFO_PARAMS_ERROR);
        }
        // 构建上下文对象
        ReturnOrderProcessingContext context =
                ReturnOrderProcessingContext.builder().returnOrderSaveReqVO(saveReqVO).build();

        // 使用模板处理
        return TemplateUtils.invokeTemplateMethod(new SaveReturnOrderProcessing(context));
    }

    @Override
    public Long submitReturnOrder(ReturnOrderSaveReqVO submitReqVO) {
        // 构建退货单上下文
        if (submitReqVO == null){
            throw exception(ErrorCodeConstants.ORDERS_INFO_PARAMS_ERROR);
        }
        // 构建上下文对象
        ReturnOrderProcessingContext context =
                ReturnOrderProcessingContext.builder().returnOrderSaveReqVO(submitReqVO).build();

        // 使用模板处理
        return TemplateUtils.invokeTemplateMethod(new SubmitReturnOrderProcessing(context));
    }

    @Override
    public ReturnOrdersInfoDetailRespVO getOrdersInfo(Long id) {
        ReturnOrderDO orderDO = returnOrderMapper.selectById(id);
        ReturnOrdersInfoDetailRespVO respVO = BeanUtils.toBean(orderDO, ReturnOrdersInfoDetailRespVO.class);
        respVO.setOrdersDetails(ordersDetailMapper.selectListByOrderId(id));
        return respVO;
    }

    @Override
    public void updateReturnOrder(ReturnOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateReturnOrderExists(updateReqVO.getId());
        // 更新
        ReturnOrderDO updateObj = BeanUtils.toBean(updateReqVO, ReturnOrderDO.class);
        returnOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteReturnOrder(Long id) {
        // 校验存在
        validateReturnOrderExists(id);
        // 删除
        returnOrderMapper.deleteById(id);
    }

    private void validateReturnOrderExists(Long id) {
        if (returnOrderMapper.selectById(id) == null) {
            throw exception(RETURN_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public ReturnOrderDO getReturnOrder(Long id) {
        return returnOrderMapper.selectById(id);
    }

    @Override
    public PageResult<ReturnOrderDO> getReturnOrderPage(ReturnOrderPageReqVO pageReqVO) {
        return returnOrderMapper.selectPage(pageReqVO);
    }

}