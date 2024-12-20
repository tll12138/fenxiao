package cn.iocoder.yudao.module.fx.service;

import cn.iocoder.yudao.module.fx.service.ordersinfo.OrdersInfoService;
import cn.iocoder.yudao.module.fx.service.customeraddress.CustomerAddressService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ServiceFactory {
    
    @Resource
    private OrdersInfoService ordersInfoService;
    
    @Resource
    private CustomerAddressService customerAddressService;
    
    public OrdersInfoService getOrdersInfoService() {
        return ordersInfoService;
    }
    
    public CustomerAddressService getCustomerAddressService() {
        return customerAddressService;
    }
} 