package cn.iocoder.yudao.module.fx.dal.mysql.customeraddress;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.customeraddress.vo.*;

/**
 * 分销商地址 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface CustomerAddressMapper extends BaseMapperX<CustomerAddressDO> {

    default PageResult<CustomerAddressDO> selectPage(CustomerAddressPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CustomerAddressDO>()
                .eqIfPresent(CustomerAddressDO::getDistributorId, reqVO.getDistributorId())
                .eqIfPresent(CustomerAddressDO::getManager, reqVO.getManager())
                .eqIfPresent(CustomerAddressDO::getPhone, reqVO.getPhone())
                .eqIfPresent(CustomerAddressDO::getProvince, reqVO.getProvince())
                .eqIfPresent(CustomerAddressDO::getCity, reqVO.getCity())
                .eqIfPresent(CustomerAddressDO::getDistrict, reqVO.getDistrict())
                .eqIfPresent(CustomerAddressDO::getAddress, reqVO.getAddress())
                .betweenIfPresent(CustomerAddressDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CustomerAddressDO::getId));
    }

    default List<CustomerAddressDO> selectListById(Long id){
        return selectList(new LambdaQueryWrapperX<CustomerAddressDO>()
                .eqIfPresent(CustomerAddressDO::getId, id)
                .orderByDesc(CustomerAddressDO::getCreateTime));
    };
}