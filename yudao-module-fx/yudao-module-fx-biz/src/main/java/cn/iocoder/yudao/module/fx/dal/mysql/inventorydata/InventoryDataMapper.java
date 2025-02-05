package cn.iocoder.yudao.module.fx.dal.mysql.inventorydata;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.inventorydata.vo.InventoryDataPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.inventorydata.InventoryDataDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * 分销商品库存 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface InventoryDataMapper extends BaseMapperX<InventoryDataDO> {

    default PageResult<InventoryDataDO> selectPage(InventoryDataPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InventoryDataDO>()
                .betweenIfPresent(InventoryDataDO::getCreateTime, new LocalDateTime[]{reqVO.getCreateTime()})
                .eqIfPresent(InventoryDataDO::getSkuId, reqVO.getSkuId())
                .eqIfPresent(InventoryDataDO::getScanCode, reqVO.getScanCode())
                .eqIfPresent(InventoryDataDO::getGoodId, reqVO.getGoodId())
                .eqIfPresent(InventoryDataDO::getQty, reqVO.getQty())
                .eqIfPresent(InventoryDataDO::getDefectiveQty, reqVO.getDefectiveQty())
                .likeIfPresent(InventoryDataDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(InventoryDataDO::getType, reqVO.getType())
                .eqIfPresent(InventoryDataDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(InventoryDataDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(InventoryDataDO::getSaleQty, reqVO.getSaleQty())
                .eqIfPresent(InventoryDataDO::getChannel, reqVO.getChannel())
                .eqIfPresent(InventoryDataDO::getBrand, reqVO.getBrand())
                .eqIfPresent(InventoryDataDO::getValue, reqVO.getValue())
                .likeIfPresent(InventoryDataDO::getName, reqVO.getName())
                .orderByDesc(InventoryDataDO::getId));
    }

}