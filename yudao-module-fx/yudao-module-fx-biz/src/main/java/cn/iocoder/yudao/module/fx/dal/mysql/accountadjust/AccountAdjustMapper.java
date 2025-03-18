package cn.iocoder.yudao.module.fx.dal.mysql.accountadjust;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.accountadjust.AccountAdjustDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.accountadjust.vo.*;

/**
 * 分销账户调整 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface AccountAdjustMapper extends BaseMapperX<AccountAdjustDO> {

    default PageResult<AccountAdjustDO> selectPage(AccountAdjustPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AccountAdjustDO>()
                .eqIfPresent(AccountAdjustDO::getOrderNo, reqVO.getOrderNo())
                .eqIfPresent(AccountAdjustDO::getReason, reqVO.getReason())
                .eqIfPresent(AccountAdjustDO::getAccount, reqVO.getAccount())
                .eqIfPresent(AccountAdjustDO::getRemark, reqVO.getRemark())
                .eqIfPresent(AccountAdjustDO::getSubmiter, reqVO.getSubmiter())
                .eqIfPresent(AccountAdjustDO::getAdjAmount, reqVO.getAdjAmount())
                .eqIfPresent(AccountAdjustDO::getAfterAmount, reqVO.getAfterAmount())
                .eqIfPresent(AccountAdjustDO::getNowAmount, reqVO.getNowAmount())
                .eqIfPresent(AccountAdjustDO::getCompany, reqVO.getCompany())
                .eqIfPresent(AccountAdjustDO::getNowTempAmount, reqVO.getNowTempAmount())
                .eqIfPresent(AccountAdjustDO::getAfterTempAmount, reqVO.getAfterTempAmount())
                .eqIfPresent(AccountAdjustDO::getType, reqVO.getType())
                .betweenIfPresent(AccountAdjustDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AccountAdjustDO::getId));
    }

}