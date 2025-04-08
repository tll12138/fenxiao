package cn.iocoder.yudao.module.fx.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.fx.service.jstorderout.JstOrderOutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 销售单自动发货并通知 Job
 *
 * @author tll
 */
@Slf4j
@Component("SaleAutoSendJob")
public class SaleAutoSendJob implements JobHandler {

    @Resource
    private JstOrderOutService jstOrderOutService;

    @Override
    @TenantIgnore
    public String execute(String param) {
        jstOrderOutService.autoSendAndDingTalk();
        log.info("[execute][销售单自动发货并通知]");
        return "销售单自动发货并通知";
    }

}
