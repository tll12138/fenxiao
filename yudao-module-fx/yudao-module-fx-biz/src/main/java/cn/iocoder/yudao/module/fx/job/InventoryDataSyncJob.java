package cn.iocoder.yudao.module.fx.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.fx.service.inventorydata.InventoryDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每天定时同步商品库存 Job
 *
 * @author tll
 */
@Slf4j
@Component("InventoryDataSyncJob")
public class InventoryDataSyncJob implements JobHandler {

    @Resource
    private InventoryDataService inventoryDataService;

    @Override
    @TenantIgnore
    public String execute(String param) throws InterruptedException {
        inventoryDataService.syncInventoryData();
        log.info("[execute][定时同步商品库存]");
        return "定时执行同步商品库存";
    }

}
