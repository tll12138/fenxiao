package cn.iocoder.yudao.module.fx.dal.mysql.carcptaud;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.carcptaud.vo.CaRcptAudPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.carcptaud.CaRcptAudDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客商账户收款审核 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface CaRcptAudMapper extends BaseMapperX<CaRcptAudDO> {

    default PageResult<CaRcptAudDO> selectPage(CaRcptAudPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CaRcptAudDO>()
                .eqIfPresent(CaRcptAudDO::getOrderNo, reqVO.getOrderNo())
                .eqIfPresent(CaRcptAudDO::getReason, reqVO.getReason())
                .eqIfPresent(CaRcptAudDO::getPayType, reqVO.getPayType())
                .eqIfPresent(CaRcptAudDO::getPayWarrant, reqVO.getPayWarrant())
                .eqIfPresent(CaRcptAudDO::getAccount, reqVO.getAccount())
                .eqIfPresent(CaRcptAudDO::getAccountName, reqVO.getAccountName())
                .eqIfPresent(CaRcptAudDO::getReceive, reqVO.getReceive())
                .eqIfPresent(CaRcptAudDO::getRemark, reqVO.getRemark())
                .eqIfPresent(CaRcptAudDO::getCustomer, reqVO.getCustomer())
                .likeIfPresent(CaRcptAudDO::getCustomerName, reqVO.getCustomerName())
                .eqIfPresent(CaRcptAudDO::getSoId, reqVO.getSoId())
                .eqIfPresent(CaRcptAudDO::getSubmiter, reqVO.getSubmiter())
                .likeIfPresent(CaRcptAudDO::getSubmiterName, reqVO.getSubmiterName())
                .eqIfPresent(CaRcptAudDO::getPaymentAccount, reqVO.getPaymentAccount())
                .eqIfPresent(CaRcptAudDO::getCustLevel, reqVO.getCustLevel())
                .likeIfPresent(CaRcptAudDO::getPaymentAccountName, reqVO.getPaymentAccountName())
                .eqIfPresent(CaRcptAudDO::getIsRepeat, reqVO.getIsRepeat())
                .eqIfPresent(CaRcptAudDO::getBusinessEntity, reqVO.getBusinessEntity())
                .betweenIfPresent(CaRcptAudDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(CaRcptAudDO::getIsWeek, reqVO.getIsWeek())
                .orderByDesc(CaRcptAudDO::getId));
    }

}