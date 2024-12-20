package cn.iocoder.yudao.module.fx.dal.mysql.returnorder;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorder.ReturnOrderDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.*;

/**
 * FX 销售退货单 Mapper
 *
 * @author 员工
 */
@Mapper
public interface ReturnOrderMapper extends BaseMapperX<ReturnOrderDO> {

    default PageResult<ReturnOrderDO> selectPage(ReturnOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReturnOrderDO>()
                .eqIfPresent(ReturnOrderDO::getOrderId, reqVO.getOrderId())
                .betweenIfPresent(ReturnOrderDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(ReturnOrderDO::getOriginOrder, reqVO.getOriginOrder())
                .eqIfPresent(ReturnOrderDO::getReturnUserId, reqVO.getReturnUserId())
                .eqIfPresent(ReturnOrderDO::getLogisticsNumber, reqVO.getLogisticsNumber())
                .eqIfPresent(ReturnOrderDO::getIsToErp, reqVO.getIsToErp())
                .betweenIfPresent(ReturnOrderDO::getToErpTime, reqVO.getToErpTime())
                .orderByDesc(ReturnOrderDO::getId));
    }

}