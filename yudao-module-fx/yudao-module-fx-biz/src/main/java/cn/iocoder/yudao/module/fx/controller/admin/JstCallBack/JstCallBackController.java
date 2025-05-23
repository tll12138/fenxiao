package cn.iocoder.yudao.module.fx.controller.admin.JstCallBack;

import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.module.fx.dal.dataobject.jushuitanapi.AfterSalesRequest;
import cn.iocoder.yudao.module.fx.dal.dataobject.jushuitanapi.JstApiResponse;
import cn.iocoder.yudao.module.fx.dal.dataobject.jushuitanapi.LogisticsRequest;
import cn.iocoder.yudao.module.fx.service.ordersinfo.OrdersInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Tag(name = "管理后台 - 聚水潭回调")
@RestController
@RequestMapping("/jushuitan")
@Slf4j
public class JstCallBackController {

    @Resource
    private OrdersInfoService ordersInfoService;

    @PostMapping("/logistics")
    @Operation(summary = "聚水潭物流同步回调接口")
    public String logisticsSync(
            @RequestParam("ts") Long timestamp,
            @RequestParam("partnerid") String partnerId,
            @RequestParam("method") String method,
            @RequestParam("sign") String sign,
//            @RequestParam("shopid") String shopId,
            @RequestBody LogisticsRequest logisticsRequest) {

        // 处理逻辑：验证签名、解析数据等
        System.out.println("timestamp: " + timestamp);
        System.out.println("partnerId: " + partnerId);
        System.out.println("method: " + method);
        System.out.println("sign: " + sign);
//        System.out.println("shopId: " + shopId);
        System.out.println("Logistics Request: " + logisticsRequest);
        // 调用服务层处理逻辑
        ordersInfoService.processLogisticsSync(logisticsRequest);
        // 返回响应
        return JSONUtil.parse(JstApiResponse.builder().code(0).msg("success").build()).toString();
    }

    @PutMapping("/afterSales")
    @Operation(summary = "聚水潭售后收货回调接口")
    public String afterSalesSync(
            @RequestParam("ts") Long timestamp,
            @RequestParam("partnerid") String partnerId,
            @RequestParam("method") String method,
            @RequestParam("sign") String sign,
//            @RequestParam("shopid") String shopId,
            @RequestBody AfterSalesRequest afterSalesRequest) {
        // 处理逻辑：验证签名、解析数据等
        System.out.println("timestamp: " + timestamp);
        System.out.println("partnerId: " + partnerId);
        System.out.println("method: " + method);
        System.out.println("sign: " + sign);
//        System.out.println("shopId: " + shopId);
        System.out.println("afterSales Request: " + afterSalesRequest);
        // 调用服务层处理逻辑
        ordersInfoService.processAfterSalesSync(afterSalesRequest);

        // 返回响应
        return JSONUtil.parse(JstApiResponse.builder().code(0).msg("success").build()).toString();
    }

}