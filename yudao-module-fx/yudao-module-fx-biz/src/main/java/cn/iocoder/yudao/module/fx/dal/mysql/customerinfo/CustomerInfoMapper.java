package cn.iocoder.yudao.module.fx.dal.mysql.customerinfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.*;

/**
 * 分销商基础信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface CustomerInfoMapper extends BaseMapperX<CustomerInfoDO> {

    default PageResult<CustomerInfoDO> selectPage(CustomerInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CustomerInfoDO>()
                .eqIfPresent(CustomerInfoDO::getSupplierId, reqVO.getSupplierId())
                .eqIfPresent(CustomerInfoDO::getDistributorNum, reqVO.getDistributorNum())
                .likeIfPresent(CustomerInfoDO::getDistributorName, reqVO.getDistributorName())
                .eqIfPresent(CustomerInfoDO::getSubCompany, reqVO.getSubCompany())
                .likeIfPresent(CustomerInfoDO::getDisplayName, reqVO.getDisplayName())
                .eqIfPresent(CustomerInfoDO::getBelongTo, reqVO.getBelongTo())
                .eqIfPresent(CustomerInfoDO::getDistributorLevel, reqVO.getDistributorLevel())
                .eqIfPresent(CustomerInfoDO::getIsCooperate, reqVO.getIsCooperate())
                .eqIfPresent(CustomerInfoDO::getIsFreeze, reqVO.getIsFreeze())
                .eqIfPresent(CustomerInfoDO::getCustomerChannelDistribute, reqVO.getCustomerChannelDistribute())
                .eqIfPresent(CustomerInfoDO::getBrand, reqVO.getBrand())
                .eqIfPresent(CustomerInfoDO::getCustomerType, reqVO.getCustomerType())
                .betweenIfPresent(CustomerInfoDO::getLatestOrderDate, reqVO.getLatestOrderDate())
                .betweenIfPresent(CustomerInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CustomerInfoDO::getId));
    }

}