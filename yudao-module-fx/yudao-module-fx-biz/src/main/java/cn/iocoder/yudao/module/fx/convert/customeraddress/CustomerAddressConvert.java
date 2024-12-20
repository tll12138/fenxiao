package cn.iocoder.yudao.module.fx.convert.customeraddress;

import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author zrl
 * @date 2024/11/6
 */
@Mapper
public interface CustomerAddressConvert {

    CustomerAddressConvert INSTANCE = Mappers.getMapper(CustomerAddressConvert.class);

    @Mapping(target = "id", ignore = true)
    CustomerAddressDO convert(OrdersInfoDO ordersInfoDO);
}
