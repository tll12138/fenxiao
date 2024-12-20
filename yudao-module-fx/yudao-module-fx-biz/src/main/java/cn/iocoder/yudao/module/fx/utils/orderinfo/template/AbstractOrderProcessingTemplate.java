package cn.iocoder.yudao.module.fx.utils.orderinfo.template;

import cn.iocoder.yudao.module.fx.utils.orderinfo.OrderProcessingContext;
import cn.iocoder.yudao.module.fx.utils.template.OrderProcessingTemplate;

public abstract class AbstractOrderProcessingTemplate extends OrderProcessingTemplate {
    
    protected final OrderProcessingContext context;

    public AbstractOrderProcessingTemplate(OrderProcessingContext orderContext) {
        super(orderContext);
        this.context = orderContext; // 直接将转换后的上下文对象存储
    }
}