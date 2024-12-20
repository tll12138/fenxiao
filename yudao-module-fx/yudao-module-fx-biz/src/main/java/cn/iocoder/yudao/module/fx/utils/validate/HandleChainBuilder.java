package cn.iocoder.yudao.module.fx.utils.validate;

/**
 * 构建责任链
 */
public class HandleChainBuilder{
    private ValidationHandler firstHandler;
    private ValidationHandler lastHandler;

    // 添加处理者到链条中
    public HandleChainBuilder addHandler(ValidationHandler handler) {
        if (firstHandler == null) {
            firstHandler = handler;
        } else {
            lastHandler.setNext(handler);
        }
        lastHandler = handler;
        return this;
    }

    // 返回构建好的责任链
    public ValidationHandler build() {
        if (firstHandler == null) {
            throw new IllegalStateException("责任连不能为空");
        }
        return firstHandler;
    }
}