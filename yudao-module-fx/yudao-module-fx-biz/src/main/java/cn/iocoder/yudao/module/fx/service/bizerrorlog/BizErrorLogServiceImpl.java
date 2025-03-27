package cn.iocoder.yudao.module.fx.service.bizerrorlog;

import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.module.fx.dal.dataobject.bizerrorlog.BizErrorLogDO;
import cn.iocoder.yudao.module.fx.dal.mysql.bizerrorlog.BizErrorLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 业务错误日志 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class BizErrorLogServiceImpl implements BizErrorLogService {

    @Resource
    private BizErrorLogMapper bizErrorLogMapper;


    /**
     * 创建业务错误日志
     */
    @Override
    public void createBizErrorLog(String module, String type, String bizId, Map<String, Object> errorData, String errorMsg) {
        BizErrorLogDO log = new BizErrorLogDO();
        log.setModule(module);
        log.setType(type);
        log.setBizId(bizId);
        log.setErrorData(JSONUtil.toJsonStr(errorData));
        log.setErrorMsg(errorMsg);
        log.setCreateTime(LocalDateTime.now());
        log.setUserId(getLoginUserId());
        // 可以添加用户信息、请求信息等
        bizErrorLogMapper.insert(log);
    }
}