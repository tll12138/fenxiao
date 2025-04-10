package cn.iocoder.yudao.module.fx.service.ordersdetail;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.ordersdetail.vo.OrdersDetailPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersdetail.vo.OrdersDetailSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersdetail.OrdersDetailDO;
import cn.iocoder.yudao.module.fx.dal.mysql.ordersdetail.OrdersDetailMapper;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.ORDERS_DETAIL_NOT_EXISTS;

/**
 * 分销-销售订单明细 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class OrdersDetailServiceImpl implements OrdersDetailService {

    @Resource
    private OrdersDetailMapper ordersDetailMapper;

    @Override
    public Long createOrdersDetail(OrdersDetailSaveReqVO createReqVO) {
        // 插入
        OrdersDetailDO ordersDetail = BeanUtils.toBean(createReqVO, OrdersDetailDO.class);
        ordersDetailMapper.insert(ordersDetail);
        // 返回
        return ordersDetail.getId();
    }

    @Override
    public void updateOrdersDetail(OrdersDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateOrdersDetailExists(updateReqVO.getId());
        // 更新
        OrdersDetailDO updateObj = BeanUtils.toBean(updateReqVO, OrdersDetailDO.class);
        ordersDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrdersDetail(Long id) {
        // 校验存在
        validateOrdersDetailExists(id);
        // 删除
        ordersDetailMapper.deleteById(id);
    }

    private void validateOrdersDetailExists(Long id) {
        if (ordersDetailMapper.selectById(id) == null) {
            throw exception(ORDERS_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public OrdersDetailDO getOrdersDetail(Long id) {
        return ordersDetailMapper.selectById(id);
    }

    @Override
    public PageResult<OrdersDetailDO> getOrdersDetailPage(OrdersDetailPageReqVO pageReqVO) {
        return ordersDetailMapper.selectPage(pageReqVO);
    }

    /**
     * 获得分销-销售订单明细list
     *
     * @return 分销-销售订单明细
     */
    @Override
    public List<OrdersDetailDO> getOrdersDetailByOrderId(Long orderId) {
        return ordersDetailMapper.selectList(new LambdaQueryWrapper<OrdersDetailDO>().eq(OrdersDetailDO::getOrderId, orderId));
    }

    /**
     * 批量更新
     */
    @Override
    public void updateBatchById(List<OrdersDetailDO> saveDetailList) {
        if (CollectionUtil.isEmpty(saveDetailList)) {
            return;
        }
        ordersDetailMapper.updateBatch(saveDetailList);
    }

    /**
     * 批量保存
     */
    @Override
    public void saveBatch(List<OrdersDetailDO> details) {
        if (CollectionUtil.isEmpty(details)) {
            return;
        }
        ordersDetailMapper.insertBatch(details);
    }

}