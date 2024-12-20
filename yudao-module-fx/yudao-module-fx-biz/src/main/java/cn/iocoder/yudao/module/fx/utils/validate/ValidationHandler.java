package cn.iocoder.yudao.module.fx.utils.validate;

import cn.iocoder.yudao.module.fx.utils.template.BaseProcessingContext;
import lombok.Setter;

@Setter
public abstract class ValidationHandler{

    protected ValidationHandler next;

    public abstract void handle(BaseProcessingContext context);

    protected void nextHandle(BaseProcessingContext context) {
        // 如果有下一个处理器，继续执行链条
        if (next != null){
            next.handle(context);
        }
    }
}
