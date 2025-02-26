package cn.iocoder.yudao.module.fx.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.fx.service.ec2jstorder.Ec2jstOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tll
 * @date 2025-02-26 15:04:34
 */
@Slf4j
@Component("OrderUploadJob")
public class OrderUploadJob implements JobHandler {
    @Resource
    private Ec2jstOrderService ec2jstOrderService;

    @Override
    public String execute(String param) throws Exception {
        ec2jstOrderService.uploadOrders();
        log.info("[execute][定时订单上传]");
        return "定时执行订单上传";
    }
}
