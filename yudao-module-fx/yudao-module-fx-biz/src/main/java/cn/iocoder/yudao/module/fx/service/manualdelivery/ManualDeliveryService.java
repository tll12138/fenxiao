package cn.iocoder.yudao.module.fx.service.manualdelivery;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.manualdelivery.vo.ManualDeliveryPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.manualdelivery.vo.ManualDeliverySaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.manualdelivery.ManualDeliveryDO;

import javax.validation.Valid;

/**
 * 手动发货信息 Service 接口
 *
 * @author 管理员
 */
public interface ManualDeliveryService {

    /**
     * 创建手动发货信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createManualDelivery(@Valid ManualDeliverySaveReqVO createReqVO);

    /**
     * 更新手动发货信息
     *
     * @param updateReqVO 更新信息
     */
    void updateManualDelivery(@Valid ManualDeliverySaveReqVO updateReqVO);

    /**
     * 执行手动发货
     */
    void handleManualShipment(Long id);

    /**
     * 删除手动发货信息
     *
     * @param id 编号
     */
    void deleteManualDelivery(Long id);

    /**
     * 获得手动发货信息
     *
     * @param id 编号
     * @return 手动发货信息
     */
    ManualDeliveryDO getManualDelivery(Long id);

    /**
     * 获得手动发货信息分页
     *
     * @param pageReqVO 分页查询
     * @return 手动发货信息分页
     */
    PageResult<ManualDeliveryDO> getManualDeliveryPage(ManualDeliveryPageReqVO pageReqVO);

}