package cn.iocoder.yudao.module.fx.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.fx.service.customeraccount.CustomerAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每天定时为没有资金账户的经销商创建一个资金账号，并初始化值
 *
 * @author tll
 */
@Slf4j
@Component("CustomerAccountGenJob")
public class CustomerAccountGenJob implements JobHandler {

    @Resource
    private CustomerAccountService customerAccountService;

    @Override
    @TenantIgnore
    public String execute(String param) {
        customerAccountService.accountAutoConstructor();
        log.info("[execute][定时创建资金账号]");
        return "定时创建资金账号";
    }

}
