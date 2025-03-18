package cn.iocoder.yudao.module.fx.dal.mysql.monsettlement;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.monsettlement.MonSettlementDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.monsettlement.vo.*;

/**
 * 分销账户月结 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface MonSettlementMapper extends BaseMapperX<MonSettlementDO> {

    default PageResult<MonSettlementDO> selectPage(MonSettlementPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MonSettlementDO>()
                .eqIfPresent(MonSettlementDO::getMonth, reqVO.getMonth())
                .eqIfPresent(MonSettlementDO::getTotalAmount, reqVO.getTotalAmount())
                .eqIfPresent(MonSettlementDO::getWithheldAmount, reqVO.getWithheldAmount())
                .eqIfPresent(MonSettlementDO::getAvailableAmount, reqVO.getAvailableAmount())
                .betweenIfPresent(MonSettlementDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MonSettlementDO::getAccount, reqVO.getAccount())
                .eqIfPresent(MonSettlementDO::getCompany, reqVO.getCompany())
                .orderByDesc(MonSettlementDO::getId));
    }

}