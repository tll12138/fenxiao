package cn.iocoder.yudao.module.fx.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.fx.service.customerinfo.CustomerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每天定时同步分销商 Job
 *
 * @author tll
 */
@Slf4j
@Component
public class CustomerSyncJob implements JobHandler {

    @Resource
    private CustomerInfoService customerInfoService;

    @Override
    @TenantIgnore
    public String execute(String param) {
        customerInfoService.syncCustomers();
        log.info("[execute][定时同步分销商]");
        return "定时执行同步分销商";
    }

}
