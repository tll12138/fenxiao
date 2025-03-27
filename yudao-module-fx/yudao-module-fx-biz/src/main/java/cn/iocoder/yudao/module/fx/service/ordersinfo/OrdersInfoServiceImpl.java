package cn.iocoder.yudao.module.fx.service.ordersinfo;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.fx.constant.FieldConstant;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.ProcessInstanceCancelReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.module.fx.dal.mysql.ordersdetail.OrdersDetailMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.ordersinfo.OrdersInfoMapper;
import cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.service.bigcustomeraddress.BigCustomerAddressService;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import cn.iocoder.yudao.module.fx.utils.orderinfo.OrderProcessingContext;
import cn.iocoder.yudao.module.fx.utils.orderinfo.template.SaveOrderProcessing;
import cn.iocoder.yudao.module.fx.utils.orderinfo.template.SubmitOrderProcessing;
import cn.iocoder.yudao.module.fx.utils.template.TemplateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.ORDERS_INFO_NOT_EXISTS;

/**
 * 销售单 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class OrdersInfoServiceImpl implements OrdersInfoService {

    @Resource
    private OrdersInfoMapper ordersInfoMapper;
    @Resource
    private OrdersDetailMapper ordersDetailMapper;
    @Resource
    private BpmProcessInstanceApi processInstanceApi;
    @Resource
    private BigCustomerAddressService bigCustomerAddressService;

    /**
     * 销售单对应的流程定义 KEY
     */
    public static final String PROCESS_KEY = "sale_audit";

    @Override
    public Long saveOrdersInfo(OrdersInfoSaveReqVO saveReqVO) {
        if (saveReqVO == null) {
            throw exception(ErrorCodeConstants.ORDERS_INFO_PARAMS_ERROR);
        }
        // 构建上下文对象
        OrderProcessingContext context = OrderProcessingContext.builder().ordersInfoSaveReqVO(saveReqVO).build();

        // 构建 保存订单的 模板对象
        return TemplateUtils.invokeTemplateMethod(new SaveOrderProcessing(context));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrdersInfo(OrdersInfoSaveReqVO createReqVO) {
        if (createReqVO == null) {
            throw exception(ErrorCodeConstants.ORDERS_INFO_PARAMS_ERROR);
        }
        // 构建上下文对象
        OrderProcessingContext context = OrderProcessingContext.builder().ordersInfoSaveReqVO(createReqVO).build();

        //  构建 提交订单的 模板对象
        Long id = TemplateUtils.invokeTemplateMethod(new SubmitOrderProcessing(context));

        if (id == null) {
            throw exception(ErrorCodeConstants.SYSTEM_ERROR);
        }
        //更新销售单的一些字段
        ordersInfoMapper.updateSaleMain(id);
        String bigCustomerAddress = createReqVO.getBigCustomerAddress();
        if (bigCustomerAddress != null) {
            bigCustomerAddressService.updateBigCustomerAddressCountById(Long.parseLong(bigCustomerAddress));
        }
        //获取当前用户的ID
        Long userId = getLoginUserId();
        log.info("用户ID:{}", userId);
        // 发起 BPM 流程
        Map<String, Object> processInstanceVariables = BeanUtil.beanToMap(createReqVO);
        String processInstanceId = processInstanceApi.createProcessInstance(userId,
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(id)));

        // 将工作流的编号，更新到销售单中
        ordersInfoMapper.updateById(
                new OrdersInfoDO()
                        .setId(id)
                        .setProcessInstanceId(processInstanceId)
                        .setOrderStatus(OrderStatusType.AUDITING.getType()));
        // 返回
        return id;
    }

    @Override
    public void updateOrdersInfoStatus(Long id, Integer orderStatusType) {
        validateOrdersInfoExists(id);
        ordersInfoMapper.updateById(new OrdersInfoDO()
                .setId(id)
                .setOrderStatus(orderStatusType));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrdersInfo(OrdersInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateOrdersInfoExists(updateReqVO.getId());
        // 更新
        OrdersInfoDO updateObj = BeanUtils.toBean(updateReqVO, OrdersInfoDO.class);
        ordersInfoMapper.updateById(updateObj);

        // 更新子表
        updateOrdersDetailList(updateReqVO.getId(), updateReqVO.getOrdersDetails());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrdersInfo(Long id) {
        // 校验存在
        validateOrdersInfoExists(id);
        // 删除
        ordersInfoMapper.deleteById(id);

        // 删除子表
        deleteOrdersDetailByOrderId(id);
    }

    private void validateOrdersInfoExists(Long id) {
        if (ordersInfoMapper.selectById(id) == null) {
            throw exception(ORDERS_INFO_NOT_EXISTS);
        }
    }

    @Override
    public OrdersInfoDetailRespVO getOrdersInfo(Long id) {
        OrdersInfoDO ordersInfoDO = ordersInfoMapper.selectById(id);
        OrdersInfoDetailRespVO respVO = BeanUtils.toBean(ordersInfoDO, OrdersInfoDetailRespVO.class);
        respVO.setOrdersDetails(ordersDetailMapper.selectListByOrderId(id));
        return respVO;
    }

    /**
     * 根据流程编号获得销售单
     *
     * @param processInstanceId 流程编号
     * @return 销售单
     */
    @Override
    public OrdersInfoDetailRespVO getOrdersInfo(String processInstanceId) {
        OrdersInfoDO ordersInfoDO = ordersInfoMapper.selectOne(new LambdaQueryWrapperX<OrdersInfoDO>().eq(OrdersInfoDO::getProcessInstanceId, processInstanceId));
        if (ordersInfoDO != null) {
            OrdersInfoDetailRespVO respVO = BeanUtils.toBean(ordersInfoDO, OrdersInfoDetailRespVO.class);
            respVO.setOrdersDetails(ordersDetailMapper.selectListByOrderId(ordersInfoDO.getId()));
            return respVO;
        }
        return null;
    }

    @Override
    public PageResult<OrdersInfoDO> getOrdersInfoPage(OrdersInfoPageReqVO pageReqVO) {
        return ordersInfoMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（分销-销售订单明细） ====================

    @Override
    public List<OrdersDetailDO> getOrdersDetailListByOrderId(Long orderId) {
        return ordersDetailMapper.selectListByOrderId(orderId);
    }

    private void createOrdersDetailList(Long orderId, List<OrdersDetailDO> list) {
        //检查所有的商品品牌 是否是同一个品牌
        long count = list.stream().map(OrdersDetailDO::getBrand).distinct().count();
        if (count > 1) {
            throw exception(ErrorCodeConstants.ORDERS_DETAIL_BRAND_NOT_SAME);
        }
        list.forEach(o -> {
            Long id = o.getId();
            o.setGoodsId(id);
            o.setId(null);
            o.setOrderId(orderId);
        });
        ordersDetailMapper.insertBatch(list);
    }

    private void updateOrdersDetailList(Long orderId, List<OrdersDetailDO> list) {
        deleteOrdersDetailByOrderId(orderId);
        list.forEach(o -> o.setId(null).setUpdater(null).setUpdateTime(null)); // 解决更新情况下：1）id 冲突；2）updateTime 不更新
        createOrdersDetailList(orderId, list);
    }

    private void deleteOrdersDetailByOrderId(Long orderId) {
        ordersDetailMapper.deleteByOrderId(orderId);
    }

    @Override
    public void cancelProcessInstance(Long loginUserId, ProcessInstanceCancelReqVO cancelReqVO) {
        processInstanceApi.cancelProcessInstance(getLoginUserId(), cancelReqVO.getId(), cancelReqVO.getReason());
        log.info("用户:{} 取消流程实例:{}", loginUserId, cancelReqVO.getId());
        // 更新此流程中所有代办的状态 TODO
    }

    @Override
    public List<OrdersInfoDO> getUnUploadedOrders() {
        List<OrdersInfoDO> orders = ordersInfoMapper.selectList(new LambdaQueryWrapper<OrdersInfoDO>().eq(OrdersInfoDO::getIsToErp, FieldConstant.IS_TO_ERP_UNPROCESSED));
        return CollectionUtil.isEmpty(orders) ? Collections.emptyList() : orders;
    }

    @Override
    public void saleProcess() {
        //跨境订单处理
        List<OrdersInfoDO> crossBorderOrders = ordersInfoMapper.getCrossBorderOrders();
        crossBorderOrders.forEach(o -> {
            try {
                //置为已发货
                o.setOrderStatus(OrderStatusType.SHIPPED.getType());
                o.setSendDate(LocalDate.now());
                o.setSendTime(LocalDate.now());
                //自动扣款，并且自动生成账户调整记录【类型为扣款】TODO
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}