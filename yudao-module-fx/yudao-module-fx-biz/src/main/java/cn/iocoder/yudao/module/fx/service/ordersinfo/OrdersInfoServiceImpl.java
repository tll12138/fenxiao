package cn.iocoder.yudao.module.fx.service.ordersinfo;

import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.dal.mysql.ordersdetail.OrdersDetailMapper;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.ordersinfo.OrdersInfoMapper;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.Constants.SALE;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 销售单 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class OrdersInfoServiceImpl implements OrdersInfoService {

    @Resource
    private OrdersInfoMapper ordersInfoMapper;
    @Resource
    private OrdersDetailMapper ordersDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrdersInfo(OrdersInfoSaveReqVO createReqVO) {
        // 插入
        OrdersInfoDO ordersInfo = BeanUtils.toBean(createReqVO, OrdersInfoDO.class);
        // 获取当前日期，转换格式为yyyyMMddHHmm

        LocalDateTime now = LocalDateTime.now();
        String dateStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        ordersInfo.setOrderId(String.format("%s%s", SALE, dateStr));
        ordersInfo.setOrderDate(LocalDate.now());
        ordersInfo.setOrderStatus(OrderStatusType.AUDITING.getType()); // 默认审核中
        ordersInfoMapper.insert(ordersInfo);

        // 插入子表
        createOrdersDetailList(ordersInfo.getId(), createReqVO.getOrdersDetails());
        // 返回
        return ordersInfo.getId();
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
    public OrdersInfoDO getOrdersInfo(Long id) {
        return ordersInfoMapper.selectById(id);
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
        list.forEach(o -> o.setOrderId(orderId));
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