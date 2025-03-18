package cn.iocoder.yudao.module.fx.dal.mysql.accountcollection;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.accountcollection.AccountCollectionDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.accountcollection.vo.*;

/**
 * 分销账户收款记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface AccountCollectionMapper extends BaseMapperX<AccountCollectionDO> {

    default PageResult<AccountCollectionDO> selectPage(AccountCollectionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AccountCollectionDO>()
                .eqIfPresent(AccountCollectionDO::getOrderNo, reqVO.getOrderNo())
                .eqIfPresent(AccountCollectionDO::getReason, reqVO.getReason())
                .eqIfPresent(AccountCollectionDO::getPayType, reqVO.getPayType())
                .eqIfPresent(AccountCollectionDO::getPayProof, reqVO.getPayProof())
                .eqIfPresent(AccountCollectionDO::getAccount, reqVO.getAccount())
                .eqIfPresent(AccountCollectionDO::getReceive, reqVO.getReceive())
                .eqIfPresent(AccountCollectionDO::getRemark, reqVO.getRemark())
                .eqIfPresent(AccountCollectionDO::getCustomer, reqVO.getCustomer())
                .eqIfPresent(AccountCollectionDO::getSoId, reqVO.getSoId())
                .eqIfPresent(AccountCollectionDO::getSubmiter, reqVO.getSubmiter())
                .eqIfPresent(AccountCollectionDO::getPayoutAccountId, reqVO.getPayoutAccountId())
                .eqIfPresent(AccountCollectionDO::getLevel, reqVO.getLevel())
                .likeIfPresent(AccountCollectionDO::getPayoutAccountName, reqVO.getPayoutAccountName())
                .eqIfPresent(AccountCollectionDO::getIsRepeat, reqVO.getIsRepeat())
                .eqIfPresent(AccountCollectionDO::getMainId, reqVO.getMainId())
                .betweenIfPresent(AccountCollectionDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(AccountCollectionDO::getIsWeek, reqVO.getIsWeek())
                .betweenIfPresent(AccountCollectionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AccountCollectionDO::getId));
    }

}