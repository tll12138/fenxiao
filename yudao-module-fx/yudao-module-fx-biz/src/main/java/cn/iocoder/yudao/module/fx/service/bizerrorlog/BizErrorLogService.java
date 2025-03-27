package cn.iocoder.yudao.module.fx.service.bizerrorlog;

import java.util.Map;

/**
 * 业务错误日志 Service 接口
 *
 * @author 管理员
 */
public interface BizErrorLogService {

    /**
     * 创建业务错误日志
     */
    void createBizErrorLog(String module, String type, String bizId, Map<String, Object> errorData, String errorMsg);


}