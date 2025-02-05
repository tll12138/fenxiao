package cn.iocoder.yudao.module.fx.service.inventorydata;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.inventorydata.vo.InventoryDataPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.inventorydata.vo.InventoryDataSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.inventorydata.InventoryDataDO;

import javax.validation.Valid;

/**
 * 分销商品库存 Service 接口
 *
 * @author 管理员
 */
public interface InventoryDataService {

    /**
     * 创建分销商品库存
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createInventoryData(@Valid InventoryDataSaveReqVO createReqVO);

    /**
     * 更新分销商品库存
     *
     * @param updateReqVO 更新信息
     */
    void updateInventoryData(@Valid InventoryDataSaveReqVO updateReqVO);

    /**
     * 删除分销商品库存
     *
     * @param id 编号
     */
    void deleteInventoryData(Integer id);

    /**
     * 获得分销商品库存
     *
     * @param id 编号
     * @return 分销商品库存
     */
    InventoryDataDO getInventoryData(Integer id);

    /**
     * 获得分销商品库存分页
     *
     * @param pageReqVO 分页查询
     * @return 分销商品库存分页
     */
    PageResult<InventoryDataDO> getInventoryDataPage(InventoryDataPageReqVO pageReqVO);

    /**
     * 同步商品库存信息
     */
    void syncInventoryData() throws InterruptedException;

}