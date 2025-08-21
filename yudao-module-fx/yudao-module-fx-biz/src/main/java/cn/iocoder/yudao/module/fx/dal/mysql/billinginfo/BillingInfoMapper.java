package cn.iocoder.yudao.module.fx.dal.mysql.billinginfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.billinginfo.BillingInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.billinginfo.vo.*;

/**
 * 开票信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface BillingInfoMapper extends BaseMapperX<BillingInfoDO> {

    default PageResult<BillingInfoDO> selectPage(BillingInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BillingInfoDO>()
                .eqIfPresent(BillingInfoDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(BillingInfoDO::getCompany, reqVO.getCompany())
                .eqIfPresent(BillingInfoDO::getTax, reqVO.getTax())
                .eqIfPresent(BillingInfoDO::getBank, reqVO.getBank())
                .eqIfPresent(BillingInfoDO::getAddress, reqVO.getAddress())
                .eqIfPresent(BillingInfoDO::getIsActive, reqVO.getIsActive())
                .eqIfPresent(BillingInfoDO::getRemark, reqVO.getRemark())
                .eqIfPresent(BillingInfoDO::getEmail, reqVO.getEmail())
                .betweenIfPresent(BillingInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BillingInfoDO::getId));
    }

}