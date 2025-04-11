package cn.iocoder.yudao.module.fx.service.returnorder;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.ProcessInstanceCancelReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrderPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrderSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorder.ReturnOrderDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorderdetail.ReturnOrderDetailDO;
import cn.iocoder.yudao.module.fx.dal.mysql.returnorder.ReturnOrderMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.returnorderdetail.ReturnOrderDetailMapper;
import cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.utils.returnorder.ReturnOrderProcessingContext;
import cn.iocoder.yudao.module.fx.utils.returnorder.template.SaveReturnOrderProcessing;
import cn.iocoder.yudao.module.fx.utils.returnorder.template.SubmitReturnOrderProcessing;
import cn.iocoder.yudao.module.fx.utils.template.TemplateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
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
    @Resource
    private BpmProcessInstanceApi processInstanceApi;

    /**
     * 退货单对应的流程定义 KEY
     */
    public static final String PROCESS_KEY = "sale_return_audit";

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
        Long id = TemplateUtils.invokeTemplateMethod(new SubmitReturnOrderProcessing(context));
        if (id == null) {
            throw exception(ErrorCodeConstants.SYSTEM_ERROR);
        }

        //获取当前用户的ID
        Long userId = getLoginUserId();
        log.info("用户ID:{}", userId);
        // 发起 BPM 流程
        Map<String, Object> processInstanceVariables = BeanUtil.beanToMap(submitReqVO);
        String processInstanceId = processInstanceApi.createProcessInstance(userId,
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(id)));

        // 将工作流的编号，更新到销售单中
        returnOrderMapper.update(new LambdaUpdateWrapper<ReturnOrderDO>()
                .eq(ReturnOrderDO::getId, id)
                .set(ReturnOrderDO::getProcessInstanceId, processInstanceId)
                .set(ReturnOrderDO::getOrderStatus, OrderStatusType.AUDITING.getType()));
        // 返回
        return id;
    }

    @Override
    public ReturnOrdersInfoDetailRespVO getOrdersInfo(Long id) {
        ReturnOrderDO orderDO = returnOrderMapper.selectById(id);
        ReturnOrdersInfoDetailRespVO respVO = BeanUtils.toBean(orderDO, ReturnOrdersInfoDetailRespVO.class);
        String customerMap = stringRedisTemplate.opsForValue().get("customer:info:distributor-mapping");
        if (customerMap != null) {
            Map<String, String> customerNameMap = JSONUtil.parseObj(customerMap).toBean(Map.class);
            respVO.setReturnUserName(customerNameMap.get(respVO.getReturnUserId().toString()));
        }
        respVO.setOrdersDetails(returnOrderDetailMapper.selectListByReturnOrderId(id));
        return respVO;
    }

    /**
     * 根据流程编号取消流程实例
     *
     * @param loginUserId
     * @param cancelReqVO
     */
    @Override
    public void cancelProcessInstance(Long loginUserId, ProcessInstanceCancelReqVO cancelReqVO) {
        processInstanceApi.cancelProcessInstance(getLoginUserId(), cancelReqVO.getId(), cancelReqVO.getReason());
        log.info("用户:{} 取消流程实例:{}", loginUserId, cancelReqVO.getId());
    }

    /**
     * 初始化流程实例
     */
    @Override
    public void initProcess(Long id) {
        ReturnOrdersInfoDetailRespVO ordersInfo = getOrdersInfo(id);
        // 发起 BPM 流程
        Map<String, Object> processInstanceVariables = BeanUtil.beanToMap(ordersInfo);
        String processInstanceId = processInstanceApi.createProcessInstance(getLoginUserId(),
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(id)));

        // 将工作流的编号，更新到销售单中
        returnOrderMapper.update(new LambdaUpdateWrapper<ReturnOrderDO>()
                .eq(ReturnOrderDO::getId, id)
                .set(ReturnOrderDO::getProcessInstanceId, processInstanceId)
                .set(ReturnOrderDO::getOrderStatus, OrderStatusType.AUDITING.getType()));
    }

    /**
     * 更改退换单状态
     *
     * @param id
     * @param type
     */
    @Override
    public void updateReturnOrdersInfoStatus(Long id, Integer type) {
        returnOrderMapper.update(new LambdaUpdateWrapper<ReturnOrderDO>().set(ReturnOrderDO::getOrderStatus, type).eq(ReturnOrderDO::getId, id));
    }


    @Override
    public void updateReturnOrder(ReturnOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateReturnOrderExists(updateReqVO.getId());
        // 更新
        ReturnOrderDO updateObj = BeanUtils.toBean(updateReqVO, ReturnOrderDO.class);
        returnOrderMapper.updateById(updateObj);
    }

    /**
     * 更新销售退货单转换标志
     */
    @Override
    public void updateReturnOrderByTran(ReturnOrdersInfoDetailRespVO returnOrder) {
        // 校验存在
        ReturnOrderDO orderDO = validateReturnOrderExists(returnOrder.getId());
        orderDO.setIsToErp(returnOrder.getIsToErp());
        orderDO.setToErpTime(returnOrder.getToErpTime());
        orderDO.setOrderStatus(returnOrder.getOrderStatus());
        returnOrderMapper.updateById(orderDO);
    }

    @Override
    public void deleteReturnOrder(Long id) {
        // 校验存在
        validateReturnOrderExists(id);
        // 删除
        returnOrderMapper.deleteById(id);
        returnOrderDetailMapper.delete(new LambdaQueryWrapper<ReturnOrderDetailDO>().eq(ReturnOrderDetailDO::getMainId, id));
    }

    private ReturnOrderDO validateReturnOrderExists(Long id) {
        ReturnOrderDO returnOrderDO = returnOrderMapper.selectById(id);
        if (returnOrderDO == null) {
            throw exception(RETURN_ORDER_NOT_EXISTS);
        }
        return returnOrderDO;
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
        String customerInfoJson = stringRedisTemplate.opsForValue().get("customer:info:distributor-mapping");
        if (StrUtil.isNotBlank(customerInfoJson)) {
            Long returnUserId = returnOrderDO.getReturnUserId();
            Map<Long, String> customerInfoMap = JSONUtil.parseObj(customerInfoJson).toBean(Map.class);
            customerInfoMap.get(returnUserId);
            respVO.setReturnUserName(StrUtil.emptyToDefault(customerInfoMap.get(returnUserId), StrUtil.EMPTY));
        }
        //获取数量不为0的商品详情
        respVO.setOrdersDetails(returnOrderDetailMapper.selectListByReturnOrderId(returnOrderDO.getId(), 0));
        return respVO;
    }

    @Override
    public PageResult<ReturnOrderDO> getReturnOrderPage(ReturnOrderPageReqVO pageReqVO) {
        return returnOrderMapper.selectPage(pageReqVO);
    }

}