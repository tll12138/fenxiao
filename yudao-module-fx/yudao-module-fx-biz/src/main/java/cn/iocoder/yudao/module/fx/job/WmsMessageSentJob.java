package cn.iocoder.yudao.module.fx.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.fx.service.sentmessage.SentMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tll
 * @date 2025-03-28 15:43:57
 */
@Slf4j
@Component("WmsMessageSentJob")
public class WmsMessageSentJob implements JobHandler {

    @Resource
    private SentMessageService sentMessageService;

    /**
     * 执行任务
     *
     * @param param 参数
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public String execute(String param) throws Exception {
        sentMessageService.executeSendMsg(null);
        log.info("[execute][定时推送发货消息通知]");
        return "定时推送发货消息通知";
    }
}
