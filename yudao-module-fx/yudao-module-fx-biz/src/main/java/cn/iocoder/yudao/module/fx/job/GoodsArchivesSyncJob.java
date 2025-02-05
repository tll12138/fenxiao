package cn.iocoder.yudao.module.fx.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.fx.service.goodsarchives.GoodsArchivesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每天定时同步商品资料 Job
 *
 * @author tll
 */
@Slf4j
@Component("GoodsArchivesSyncJob")
public class GoodsArchivesSyncJob implements JobHandler {

    @Resource
    private GoodsArchivesService goodsArchivesService;

    @Override
    @TenantIgnore
    public String execute(String param) {
        goodsArchivesService.syncGoodsArchives(false);
        log.info("[execute][定时同步商品资料]");
        return "定时执行同步商品资料";
    }

}
