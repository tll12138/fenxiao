package cn.iocoder.yudao.module.fx.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.fx.service.sendrepository.SendRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每月定时同步发货仓库 Job
 *
 * @author tll
 */
@Slf4j
@Component("SendRepositorySyncJob")
public class SendRepositorySyncJob implements JobHandler {

    @Resource
    private SendRepositoryService sendRepositoryService;

    @Override
    @TenantIgnore
    public String execute(String param) {
        sendRepositoryService.syncSendRepository();
        log.info("[execute][定时同步发货仓库]");
        return "定时执行同步发货仓库";
    }

}
