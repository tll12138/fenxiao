package cn.iocoder.yudao.module.fx.utils.validate;

import cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.fx.utils.template.BaseProcessingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 品牌校验处理器
 */
@Slf4j
@Service
public class BrandValidationHandler extends ValidationHandler {

    @Override
    public void handle(BaseProcessingContext context) {
        List<String> brands = context.getBrands();
        //检查所有的商品品牌 是否是同一个品牌
        long count = brands.stream().distinct().count();
        if (count > 1) {
            throw exception(ErrorCodeConstants.ORDERS_DETAIL_BRAND_NOT_SAME);
        }
        log.info("[BrandValidationHandler] 品牌校验通过...");
        nextHandle(context);
    }
}