package cn.iocoder.yudao.module.fx.service.ordersinfo;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.module.fx.dal.mysql.ordersdetail.OrdersDetailMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.ordersinfo.OrdersInfoMapper;
import cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.fx.constant.Constants.SALE;
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

    /**
     * 销售单对应的流程定义 KEY
     */
    public static final String PROCESS_KEY = "sale-order";
    @Override
    @Transactional
    public Long createOrdersInfo(OrdersInfoSaveReqVO createReqVO) {
        // 转换对象
        OrdersInfoDO ordersInfo = BeanUtils.toBean(createReqVO, OrdersInfoDO.class);
        //计算合计
        List<OrdersDetailDO> ordersDetails = createReqVO.getOrdersDetails();
        //根据商品id分组，计算数量之和
        Map<String, Integer> collect = ordersDetails.stream().collect(
                Collectors.groupingBy(OrdersDetailDO::getSkuId, Collectors.summingInt(OrdersDetailDO::getCount)));
        StringBuilder count = new StringBuilder();
        collect.forEach((k, v) -> {
            count.append(k).append("  *  ").append(v).append("\n");
        });
        // 获取当前日期，转换格式为yyyyMMddHHmm
        LocalDateTime now = LocalDateTime.now();
        String dateStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        ordersInfo.setOrderId(String.format("%s%s", SALE, dateStr));
        ordersInfo.setOrderDate(LocalDate.now());
        ordersInfo.setOrderStatus(OrderStatusType.AUDITING.getType()); // 默认审核中
        ordersInfo.setCreator(SecurityFrameworkUtils.getLoginUserNickname());
        ordersInfo.setTotalGoods(count.toString());
        ordersInfoMapper.insert(ordersInfo);
        Long orderId = ordersInfo.getId();
        // 插入子表
        createOrdersDetailList(ordersInfo.getId(), createReqVO.getOrdersDetails());

        //获取当前用户的ID
        Long userId = getLoginUserId();
        log.info("用户ID:{}", userId);
        // 发起 BPM 流程
        Map<String, Object> processInstanceVariables = BeanUtil.beanToMap(createReqVO);
        String processInstanceId = processInstanceApi.createProcessInstance(userId,
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(orderId)));

//        // 将工作流的编号，更新到 OA 请假单中
        ordersInfoMapper.updateById(new OrdersInfoDO().setId(orderId).setProcessInstanceId(processInstanceId));

        // 返回
        return ordersInfo.getId();
    }

    @Override
    public void updateOrdersInfoStatusSuccess(Long id) {
        validateOrdersInfoExists(id);
        ordersInfoMapper.updateById(
                new OrdersInfoDO().setId(id).setOrderStatus(OrderStatusType.COMPLETED.getType()));
    }

    @Override
    public void updateOrdersInfoStatusFail(Long id) {
        validateOrdersInfoExists(id);
        ordersInfoMapper.updateById(
                new OrdersInfoDO().setId(id).setOrderStatus(OrderStatusType.CREATION_FAILED.getType()));
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

}