package cn.iocoder.yudao.module.fx.utils.returnorder.template;

import cn.iocoder.yudao.module.fx.utils.returnorder.ReturnOrderProcessingContext;
import cn.iocoder.yudao.module.fx.utils.template.OrderProcessingTemplate;

public abstract class AbstractReturnOrderProcessingTemplate extends OrderProcessingTemplate {
    
    protected final ReturnOrderProcessingContext context;

    public AbstractReturnOrderProcessingTemplate(ReturnOrderProcessingContext orderContext) {
        super(orderContext);
        this.context = orderContext; // 直接将转换后的上下文对象存储
    }
}