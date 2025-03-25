package cn.iocoder.yudao.module.fx.dal.mysql.bigcustomeraddress;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.bigcustomeraddress.BigCustomerAddressDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.bigcustomeraddress.vo.*;

/**
 * 分销大客户地址 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface BigCustomerAddressMapper extends BaseMapperX<BigCustomerAddressDO> {

    default PageResult<BigCustomerAddressDO> selectPage(BigCustomerAddressPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BigCustomerAddressDO>()
                .eqIfPresent(BigCustomerAddressDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(BigCustomerAddressDO::getProvince, reqVO.getProvince())
                .eqIfPresent(BigCustomerAddressDO::getCity, reqVO.getCity())
                .eqIfPresent(BigCustomerAddressDO::getDistrict, reqVO.getDistrict())
                .eqIfPresent(BigCustomerAddressDO::getAddress, reqVO.getAddress())
                .eqIfPresent(BigCustomerAddressDO::getPerson, reqVO.getPerson())
                .eqIfPresent(BigCustomerAddressDO::getContact, reqVO.getContact())
                .eqIfPresent(BigCustomerAddressDO::getIsActive, reqVO.getIsActive())
                .eqIfPresent(BigCustomerAddressDO::getStatus, reqVO.getStatus())
                .eqIfPresent(BigCustomerAddressDO::getRemark, reqVO.getRemark())
                .eqIfPresent(BigCustomerAddressDO::getBrand, reqVO.getBrand())
                .eqIfPresent(BigCustomerAddressDO::getCount, reqVO.getCount())
                .betweenIfPresent(BigCustomerAddressDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BigCustomerAddressDO::getId));
    }

}