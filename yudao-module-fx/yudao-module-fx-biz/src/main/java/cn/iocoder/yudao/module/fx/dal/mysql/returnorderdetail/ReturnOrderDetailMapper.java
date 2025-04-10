package cn.iocoder.yudao.module.fx.dal.mysql.returnorderdetail;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.returnorderdetail.vo.ReturnOrderDetailPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorderdetail.ReturnOrderDetailDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 销售退货详情 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ReturnOrderDetailMapper extends BaseMapperX<ReturnOrderDetailDO> {

    default PageResult<ReturnOrderDetailDO> selectPage(ReturnOrderDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReturnOrderDetailDO>()
                .eqIfPresent(ReturnOrderDetailDO::getMainId, reqVO.getMainId())
                .eqIfPresent(ReturnOrderDetailDO::getSkuId, reqVO.getSkuId())
                .likeIfPresent(ReturnOrderDetailDO::getSkuName, reqVO.getSkuName())
                .eqIfPresent(ReturnOrderDetailDO::getCategory, reqVO.getCategory())
                .eqIfPresent(ReturnOrderDetailDO::getOriginalPrice, reqVO.getOriginalPrice())
                .eqIfPresent(ReturnOrderDetailDO::getReturnPrice, reqVO.getReturnPrice())
                .eqIfPresent(ReturnOrderDetailDO::getCostPrice, reqVO.getCostPrice())
                .eqIfPresent(ReturnOrderDetailDO::getCount, reqVO.getCount())
                .eqIfPresent(ReturnOrderDetailDO::getSaleAmt, reqVO.getSaleAmt())
                .eqIfPresent(ReturnOrderDetailDO::getCostAmt, reqVO.getCostAmt())
                .eqIfPresent(ReturnOrderDetailDO::getOriginalCount, reqVO.getOriginalCount())
                .eqIfPresent(ReturnOrderDetailDO::getRetType, reqVO.getRetType())
                .betweenIfPresent(ReturnOrderDetailDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReturnOrderDetailDO::getId));
    }

    /**
     * 根据退货单id获取退货详情列表
     *
     * @param orderId
     * @return
     */
    default List<ReturnOrderDetailDO> selectListByReturnOrderId(Long orderId) {
        return selectList(ReturnOrderDetailDO::getMainId, orderId);
    }

    /**
     * 根据退货单id获取不为指定数量的退货详情列表
     *
     * @param orderId
     * @param InventoryCount
     * @return
     */
    default List<ReturnOrderDetailDO> selectListByReturnOrderId(Long orderId, int InventoryCount) {
        return selectList(new LambdaQueryWrapper<ReturnOrderDetailDO>().eq(ReturnOrderDetailDO::getMainId, orderId).ne(ReturnOrderDetailDO::getCount, InventoryCount));
    }
}