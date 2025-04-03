package cn.iocoder.yudao.module.fx.service.manualdelivery;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.manualdelivery.vo.ManualDeliveryPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.manualdelivery.vo.ManualDeliverySaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstorderout.JstOrderOutDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.manualdelivery.ManualDeliveryDO;
import cn.iocoder.yudao.module.fx.dal.mysql.manualdelivery.ManualDeliveryMapper;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.service.jstorderout.JstOrderOutService;
import cn.iocoder.yudao.module.fx.service.jushuitanapi.JuShuiTanApiService;
import cn.iocoder.yudao.module.fx.service.ordersinfo.OrdersInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.MANUAL_DELIVERY_ERP_NO_NOT_EXIST;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.MANUAL_DELIVERY_NOT_EXISTS;

/**
 * 手动发货信息 Service 实现类
 *
 * @author 管理员
 */
@Slf4j
@Service
@Validated
public class ManualDeliveryServiceImpl implements ManualDeliveryService {

    @Resource
    private ManualDeliveryMapper manualDeliveryMapper;
    @Resource
    private OrdersInfoService ordersInfoService;
    @Resource
    private JstOrderOutService jstOrderOutService;
    @Resource
    private JuShuiTanApiService juShuiTanApiService;

    @Override
    public Long createManualDelivery(ManualDeliverySaveReqVO createReqVO) {
        // 插入
        ManualDeliveryDO manualDelivery = BeanUtils.toBean(createReqVO, ManualDeliveryDO.class);
        manualDeliveryMapper.insert(manualDelivery);
        // 返回
        return manualDelivery.getId();
    }

    @Override
    public void updateManualDelivery(ManualDeliverySaveReqVO updateReqVO) {
        // 校验存在
        validateManualDeliveryExists(updateReqVO.getId());
        // 更新
        ManualDeliveryDO updateObj = BeanUtils.toBean(updateReqVO, ManualDeliveryDO.class);
        manualDeliveryMapper.updateById(updateObj);
    }

    /**
     * 执行手动发货
     */
    @Override
    public void handleManualShipment(Long id) {
        // 校验存在
        ManualDeliveryDO deliveryDO = validateManualDeliveryExists(id);
        String saleId = deliveryDO.getSaleId();
        // 查询销售单
        OrdersInfoDetailRespVO info = ordersInfoService.getOrdersInfoById(Long.parseLong(saleId));
        String erpOrderNumber = info.getErpOrderNumber();
        // 合并订单出库信息
        String soId = deliveryDO.getSoId();
        if (StrUtil.isBlank(erpOrderNumber)) {
            throw exception(MANUAL_DELIVERY_ERP_NO_NOT_EXIST);
        }
        JstOrderOutDO orderOut = new JstOrderOutDO()
                .setOId(Integer.parseInt(erpOrderNumber))
                .setSoId(soId)
                .setExpressName(deliveryDO.getExpressName())
                .setExpress(deliveryDO.getExpress())
                .setExpressCode(deliveryDO.getExpressId())
                .setIsTran("0");
        jstOrderOutService.mergeOrderOut(orderOut);
        jstOrderOutService.handleCallSaleOrderOut(soId, deliveryDO.getExpressName(), deliveryDO.getExpress(), deliveryDO.getExpressId());
        deliveryDO.setStatus(OrderStatusType.SHIPPED.getType());
        manualDeliveryMapper.updateById(deliveryDO);

        //prc_jst2ec_orderout 聚水潭发货回传中间表

    }

    @Override
    public void deleteManualDelivery(Long id) {
        // 校验存在
        validateManualDeliveryExists(id);
        // 删除
        manualDeliveryMapper.deleteById(id);
    }

    private ManualDeliveryDO validateManualDeliveryExists(Long id) {
        ManualDeliveryDO manualDeliveryDO = manualDeliveryMapper.selectById(id);
        if (manualDeliveryDO == null) {
            throw exception(MANUAL_DELIVERY_NOT_EXISTS);
        }
        return manualDeliveryDO;
    }

    @Override
    public ManualDeliveryDO getManualDelivery(Long id) {
        return manualDeliveryMapper.selectById(id);
    }

    @Override
    public PageResult<ManualDeliveryDO> getManualDeliveryPage(ManualDeliveryPageReqVO pageReqVO) {
        return manualDeliveryMapper.selectPage(pageReqVO);
    }

}