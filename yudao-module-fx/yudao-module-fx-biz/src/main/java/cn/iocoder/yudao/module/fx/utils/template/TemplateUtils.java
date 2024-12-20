package cn.iocoder.yudao.module.fx.utils.template;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.SYSTEM_ERROR;

/**
 * @author zrl
 * @date 2024/12/5
 */
public class TemplateUtils {

    public static Long invokeTemplateMethod(OrderProcessingTemplate template) {
        try {
            // 调用模板方法实现业务，通过传入的不同的模板方法，实现调用不同的逻辑； 避免使用 if else
            return template.processOrder();
        }catch (Exception e){
            throw exception(new ErrorCode(SYSTEM_ERROR.getCode(), e.getMessage()));
        }
    }
}
