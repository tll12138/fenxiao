package cn.iocoder.yudao.module.fx.dal.mysql.amountadj;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.amountadj.AmountAdjDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.amountadj.vo.*;

/**
 * 分销账户资金调整记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface AmountAdjMapper extends BaseMapperX<AmountAdjDO> {

    default PageResult<AmountAdjDO> selectPage(AmountAdjPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AmountAdjDO>()
                .eqIfPresent(AmountAdjDO::getSoId, reqVO.getSoId())
                .eqIfPresent(AmountAdjDO::getAmount, reqVO.getAmount())
                .betweenIfPresent(AmountAdjDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(AmountAdjDO::getAccount, reqVO.getAccount())
                .eqIfPresent(AmountAdjDO::getRemark, reqVO.getRemark())
                .eqIfPresent(AmountAdjDO::getType, reqVO.getType())
                .eqIfPresent(AmountAdjDO::getAdjustBalance, reqVO.getAdjustBalance())
                .betweenIfPresent(AmountAdjDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AmountAdjDO::getId));
    }

}