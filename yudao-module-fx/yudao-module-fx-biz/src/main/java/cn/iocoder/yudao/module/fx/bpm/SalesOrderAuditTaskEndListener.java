package cn.iocoder.yudao.module.fx.bpm;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.sentmessage.vo.SentMessageSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.utils.SpringUtil;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.service.bizerrorlog.BizErrorLogService;
import cn.iocoder.yudao.module.fx.service.customerinfo.CustomerInfoService;
import cn.iocoder.yudao.module.fx.service.ordersinfo.OrdersInfoService;
import cn.iocoder.yudao.module.fx.service.sentmessage.SentMessageService;
import com.diboot.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@Component
@Slf4j
public class SalesOrderAuditTaskEndListener {

    /**
     * 销售单审核归档节点动作
     */
    @Transactional(rollbackFor = Exception.class)
    public void execute(DelegateExecution delegateExecution) {
        String errorMsg = StrUtil.EMPTY;
        // 通过流程实例ID关联业务数据
        final String processInstanceId = delegateExecution.getProcessInstanceId();
        try {
            //flowable流处理中不能用依赖注入，只能用SpringUtil.getObject获取bean
            OrdersInfoService ordersInfoService = SpringUtil.getObject(OrdersInfoService.class);
            OrdersInfoDetailRespVO ordersInfo = validateOrder(processInstanceId, ordersInfoService);
            CustomerInfoService customerInfoService = SpringUtil.getObject(CustomerInfoService.class);
            CustomerInfoDO customerInfo = customerInfoService.getCustomerInfo(ordersInfo.getDistributorId());
            //初始化延迟时间 fl\ec\vg 特殊
            String brand = ordersInfo.getBrand();
            final int delayMinutes = new HashSet<>(Arrays.asList("FREIOL", "E-CHANGE", "VALGER")).contains(brand) ? 3 : 8;
            String lateTime = DateUtil.format(DateUtil.offsetMinute(DateUtil.date(), delayMinutes), "yyyy-MM-dd HH:mm:ss");
            String warehouseCode = ordersInfo.getWarehouseCode();
            String requirement = ordersInfo.getRequirement();
            String orderId = ordersInfo.getOrderId();
            String msg = StrUtil.EMPTY;
            SentMessageService sentMessageService = SpringUtil.getObject(SentMessageService.class);
            final String DD_webhook = "https://oapi.dingtalk.com/robot/send?access_token=66a1668bb391683c4855e1bf6658eafbf39b62850e59e67eef2ab4cbae2377f2";
            final String DD_secret = "SEC155b0da885add21530ee3602a66309ab7e054e0047889551950ade24176b3383";
            if ("12367046".equals(warehouseCode)) {
                //菜鸟义乌仓-福来
                //效期要求
                boolean validityPeriodRequire = StrUtil.contains(requirement, "年") || StrUtil.contains(requirement, "最新");
                msg = validityPeriodRequire ? StrUtil.EMPTY : "按效期先后发货";
                //商品中是否存在小样
                Boolean checkSample = ordersInfoService.checkSample(ordersInfo.getId());
                msg = checkSample ? "纯小样" + "|" + msg : "需要扫溯源码出库" + "|" + msg;
                //箱规
                Boolean checkBoxSize = ordersInfoService.checkBoxSize(ordersInfo.getId());
                StringBuilder append = StrUtil.builder().append("|").append(requirement);
                msg = checkBoxSize ? append.append("|按箱规发货。").toString() : append.toString();
                sentMessageService.createSentMessage(new SentMessageSaveReqVO()
                        .setType("发货要求")
                        .setSendTime(LocalDateTime.parse(lateTime))
                        .setWebhook(DD_webhook)
                        .setSecret(DD_secret)
                        .setWarehouseId(Integer.valueOf(warehouseCode))
                        .setMsg("【freiol-2B出货要求】：" + "          " + "交易单号：" + orderId + "          " + "客商名称：" + customerInfo.getDisplayName() + "          " + "客商编码：" + customerInfo.getDistributorNum() + "          " + "发货要求：" + msg));
            } else if (new HashSet<>(Arrays.asList("11733495", "10790722", "10860196", "11353580")).contains(warehouseCode)) {
                //菜鸟义乌仓-EC\VALGER\GD\企鹅
                requirement = StrUtil.replace(requirement, "需要扫溯源码出库", StrUtil.EMPTY);
                if (requirement.length() > 2) {
                    msg = requirement;
                }
                if ("11353580".equals(warehouseCode)) {
                    //pp需要扫溯源码
                    msg = msg + "|需要扫溯源码出库|按箱规发货|做好产品防护!";
                } else {
                    msg = msg + "|按箱规发货|做好产品防护!";
                }
                sentMessageService.createSentMessage(new SentMessageSaveReqVO()
                        .setType("发货要求")
                        .setSendTime(LocalDateTime.parse(lateTime))
                        .setWebhook(DD_webhook)
                        .setSecret(DD_secret)
                        .setWarehouseId(Integer.valueOf(warehouseCode))
                        .setMsg("【" + brand + "-2B出货要求】：" + "          " + "交易单号：" + orderId + "          " + "客商名称：" + customerInfo.getDisplayName() + "          " + "客商编码：" + customerInfo.getDistributorNum() + "          " + "发货要求：" + msg));

            } else if ("11717238".equals(warehouseCode)) {
                //芜湖速发云仓
                requirement = StrUtil.replace(requirement, "需要扫溯源码出库", StrUtil.EMPTY);
                if (requirement.length() > 2) {
                    msg = requirement;
                }
                msg = msg + "|按箱规发货|做好产品防护!";
                sentMessageService.createSentMessage(new SentMessageSaveReqVO()
                        .setType("发货要求")
                        .setSendTime(LocalDateTime.parse(lateTime))
                        .setWebhook("http://10.10.4.30:5000/send_wechat")
                        .setSecret(StrUtil.EMPTY)
                        .setWarehouseId(Integer.valueOf(warehouseCode))
                        .setMsg("【" + brand + "-2B出货要求】：" + "          " + "交易单号：" + orderId + "          " + "客商名称：" + customerInfo.getDisplayName() + "          " + "客商编码：" + customerInfo.getDistributorNum() + "          " + "发货要求：" + msg));
            }

        } catch (BusinessException e) {
            errorMsg = e.getMessage();
            log.error("[流程{}] 业务异常: {}", processInstanceId, errorMsg);
            throw e; // 触发事务回滚
        } catch (Exception e) {
            errorMsg = StrUtil.format("系统异常: {}", e.getMessage());
            log.error("[流程{}] 处理异常: ", processInstanceId, e);
            throw new BusinessException(errorMsg);
        } finally {
            if (StrUtil.isNotBlank(errorMsg)) {
                // 统一记录业务错误日志
                BizErrorLogService bizErrorLogService = SpringUtil.getObject(BizErrorLogService.class);
                bizErrorLogService.createBizErrorLog("fx", "SalesOrderAuditTaskEndListener", processInstanceId, null, errorMsg);
            }
        }
    }

    /**
     * 订单基础校验
     *
     * @param processInstanceId 流程实例ID
     * @return 有效订单信息
     * @throws BusinessException 当订单不存在或状态异常时抛出
     */
    private OrdersInfoDetailRespVO validateOrder(String processInstanceId, OrdersInfoService ordersInfoService) {
        OrdersInfoDetailRespVO ordersInfo = ordersInfoService.getOrdersInfo(processInstanceId);
        // 订单存在性校验
        if (ordersInfo == null) {
            throw new BusinessException("流程对应的销售单不存在");
        }
        // 订单状态校验（防止重复操作）
        if (OrderStatusType.SHIPPED.getType().equals(ordersInfo.getOrderStatus())) {
            throw new BusinessException("销售单已发货，无法重复操作");
        }
        return ordersInfo;
    }
}

