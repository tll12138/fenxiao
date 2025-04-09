package cn.iocoder.yudao.module.fx.service.returnorder;

import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrderPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrderSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorder.ReturnOrderDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorderdetail.ReturnOrderDetailDO;
import cn.iocoder.yudao.module.fx.dal.mysql.returnorder.ReturnOrderMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.returnorderdetail.ReturnOrderDetailMapper;
import cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.fx.utils.returnorder.ReturnOrderProcessingContext;
import cn.iocoder.yudao.module.fx.utils.returnorder.template.SaveReturnOrderProcessing;
import cn.iocoder.yudao.module.fx.utils.returnorder.template.SubmitReturnOrderProcessing;
import cn.iocoder.yudao.module.fx.utils.template.TemplateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Map;

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
    private ReturnOrderDetailMapper returnOrderDetailMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public Long saveOrdersInfo(ReturnOrderSaveReqVO saveReqVO) {

        if (saveReqVO == null) {
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
        if (submitReqVO == null) {
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
        String customerMap = stringRedisTemplate.opsForValue().get("customer:info:distributor-mapping");
        Map<String, String> customerNameMap = JSONUtil.parseObj(customerMap).toBean(Map.class);
        respVO.setReturnUserName(customerNameMap.get(respVO.getReturnUserId().toString()));
        respVO.setOrdersDetails(returnOrderDetailMapper.selectListByReturnOrderId(id));
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
        returnOrderDetailMapper.delete(new LambdaQueryWrapper<ReturnOrderDetailDO>().eq(ReturnOrderDetailDO::getMainId, id));
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

    /**
     * 根据流程id获取销售退货单
     *
     * @param processInstanceId 流程编号
     * @return FX 销售退货单
     */
    @Override
    public ReturnOrdersInfoDetailRespVO getReturnOrderByProcessId(String processInstanceId) {
        ReturnOrderDO returnOrderDO = returnOrderMapper.selectOne(new LambdaQueryWrapper<ReturnOrderDO>().eq(ReturnOrderDO::getProcessInstanceId, processInstanceId));
        ReturnOrdersInfoDetailRespVO respVO = BeanUtils.toBean(returnOrderDO, ReturnOrdersInfoDetailRespVO.class);
        respVO.setOrdersDetails(returnOrderDetailMapper.selectListByReturnOrderId(returnOrderDO.getId()));
        return respVO;
    }

    @Override
    public PageResult<ReturnOrderDO> getReturnOrderPage(ReturnOrderPageReqVO pageReqVO) {
        return returnOrderMapper.selectPage(pageReqVO);
    }

}