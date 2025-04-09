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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * 销售单归档后动作
 */
@Component("salesOrderAuditTaskEndListener")
@Slf4j
public class SalesOrderAuditTaskEndListener {

    // 常量定义
    private static final Set<String> SPECIAL_BRANDS =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList("FREIOL", "E-CHANGE", "VALGER")));
    private static final Set<String> YIWU_WAREHOUSES =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList("11733495", "10790722", "10860196", "11353580")));
    private static final String FULAI_WAREHOUSE = "12367046";
    private static final String WUHU_WAREHOUSE = "11717238";
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String LOG_MODULE = "fx";
    private static final String LOG_TYPE = "SalesOrderAuditTaskEndListener";
    private static final String DD_WEBHOOK = "https://oapi.dingtalk.com/robot/send?access_token=66a1668bb391683c4855e1bf6658eafbf39b62850e59e67eef2ab4cbae2377f2";
    private static final String DD_SECRET = "SEC155b0da885add21530ee3602a66309ab7e054e0047889551950ade24176b3383";
    private static final String WUH_WEBHOOK = "http://10.10.4.30:5000/send_wechat";
    private static final String MESSAGE_SEPARATOR = "|";
    private static final String PP_WAREHOUSE = "11353580";
    private static final String BOX_SHIPMENT = "按箱规发货";
    private static final String PRODUCT_PROTECTION = "做好产品防护!";
    private static final String TRACEABILITY_CODE = "需要扫溯源码出库";

    /**
     * 销售单审核归档节点动作
     */
    @Transactional(rollbackFor = Exception.class)
    public void execute(DelegateExecution execution) {
        String errorMsg = StrUtil.EMPTY;
        final String processId = execution.getProcessInstanceId();
        // 通过流程实例ID关联业务数据
        try {
            //flowable流处理中不能用依赖注入，只能用SpringUtil.getObject获取bean
            OrdersInfoService ordersInfoService = SpringUtil.getObject(OrdersInfoService.class);
            OrdersInfoDetailRespVO ordersInfo = validateOrder(processId, ordersInfoService);
            CustomerInfoService customerInfoService = SpringUtil.getObject(CustomerInfoService.class);
            CustomerInfoDO customerInfo = customerInfoService.getCustomerInfo(ordersInfo.getDistributorId());
            //初始化延迟时间 fl\ec\vg 特殊
            String brand = ordersInfo.getOrdersDetails().get(0).getBrand();
            final int delayMinutes = SPECIAL_BRANDS.contains(brand) ? 3 : 8;
            String lateTime = DateUtil.format(DateUtil.offsetMinute(DateUtil.date(), delayMinutes), DATE_PATTERN);
            SentMessageService sentMessageService = SpringUtil.getObject(SentMessageService.class);
            processWarehouse(ordersInfo.getWarehouseCode(), StrUtil.blankToDefault(ordersInfo.getRequirement(), StrUtil.EMPTY), ordersInfoService, ordersInfo, sentMessageService
                    , lateTime, ordersInfo.getOrderId(), customerInfo, brand);
        } catch (BusinessException e) {
            errorMsg = handleBusinessError(processId, e);
            throw e;
        } catch (Exception e) {
            errorMsg = StrUtil.format("系统异常: {}", e.getMessage());
            log.error("[流程 {}] 处理异常", processId, e);
        } finally {
            logErrorIfNeeded(processId, errorMsg);
        }
        if (StrUtil.isNotBlank(errorMsg)) {
            throw new BusinessException(errorMsg);
        }
    }

    /**
     * 处理不同仓库的发货要求
     *
     * @param warehouseCode      仓库编码
     * @param requirement        发货要求
     * @param ordersInfoService  订单服务
     * @param ordersInfo         订单信息
     * @param sentMessageService 消息服务
     * @param lateTime           延迟发送时间
     * @param orderId            订单ID
     * @param customerInfo       客户信息
     * @param brand              品牌
     */
    private static void processWarehouse(String warehouseCode, String requirement, OrdersInfoService ordersInfoService, OrdersInfoDetailRespVO ordersInfo, SentMessageService sentMessageService, String lateTime, String orderId, CustomerInfoDO customerInfo, String brand) {

        SentMessageSaveReqVO message = buildBaseMessage(lateTime, warehouseCode, orderId, customerInfo, brand);
        if (FULAI_WAREHOUSE.equals(warehouseCode)) {
            handleFuLaiWarehouse(requirement, ordersInfoService, ordersInfo, message);
        } else if (YIWU_WAREHOUSES.contains(warehouseCode)) {
            handleYiWuWarehouse(warehouseCode, requirement, message);
        } else if (WUHU_WAREHOUSE.equals(warehouseCode)) {
            handleWuHuWarehouse(requirement, message);

        }
        sentMessageService.createSentMessage(message);
    }

    private static void handleFuLaiWarehouse(String requirement, OrdersInfoService ordersInfoService, OrdersInfoDetailRespVO ordersInfo, SentMessageSaveReqVO message) {
        //菜鸟义乌仓-福来
        boolean validityPeriodRequire = StrUtil.containsAny(requirement, "年", "最新");
        Boolean checkSample = ordersInfoService.checkSample(ordersInfo.getId());
        Boolean checkBoxSize = ordersInfoService.checkBoxSize(ordersInfo.getId());

        String msg = buildFuLaiMessage(validityPeriodRequire, checkSample, checkBoxSize, requirement);
        message.setMsg(message.getMsg() + msg)
                .setWebhook(DD_WEBHOOK)
                .setSecret(DD_SECRET);
    }

    private static void handleYiWuWarehouse(String warehouseCode, String requirement, SentMessageSaveReqVO message) {
        //菜鸟义乌仓-EC\VALGER\GD\企鹅
        String msg = StrUtil.EMPTY;
        requirement = StrUtil.replace(requirement, TRACEABILITY_CODE, StrUtil.EMPTY);

        if (requirement.length() > 2) {
            msg = requirement;
        }

        StringJoiner joiner = new StringJoiner(MESSAGE_SEPARATOR);
        if (PP_WAREHOUSE.equals(warehouseCode)) {
            joiner.add(TRACEABILITY_CODE);
        }
        joiner.add(BOX_SHIPMENT).add(PRODUCT_PROTECTION);

        message.setMsg(message.getMsg() + msg + MESSAGE_SEPARATOR + joiner)
                .setWebhook(DD_WEBHOOK)
                .setSecret(DD_SECRET);
    }

    private static void handleWuHuWarehouse(String requirement, SentMessageSaveReqVO message) {
        //芜湖速发云仓
        String msg = StrUtil.EMPTY;
        requirement = StrUtil.replace(requirement, TRACEABILITY_CODE, StrUtil.EMPTY);

        if (requirement.length() > 2) {
            msg = requirement;
        }

        message.setMsg(message.getMsg() + msg + MESSAGE_SEPARATOR + BOX_SHIPMENT + MESSAGE_SEPARATOR + PRODUCT_PROTECTION)
                .setWebhook(WUH_WEBHOOK)
                .setSecret(StrUtil.EMPTY);
    }

    // 公共消息构建方法
    private static SentMessageSaveReqVO buildBaseMessage(String lateTime, String warehouseCode,
                                                         String orderId, CustomerInfoDO customerInfo, String brand) {

        return new SentMessageSaveReqVO()
                .setSoId(orderId)
                .setType("发货要求")
                .setSendTime(LocalDateTime.parse(lateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .setWarehouseId(Integer.valueOf(warehouseCode))
                .setMsg(StrUtil.format("【{}-2B出货要求】：\n交易单号：{} \n客商名称：{} \n客商编码：{} \n发货要求：",
                        brand, orderId, customerInfo.getDisplayName(),
                        customerInfo.getDistributorNum()));
    }

    // 消息内容构建方法
    private static String buildFuLaiMessage(boolean validityPeriod, boolean checkSample,
                                            boolean checkBoxSize, String requirement) {

        StringBuilder msg = new StringBuilder();
        if (!validityPeriod) {
            msg.append("按效期先后发货");
        }
        msg.append(checkSample ? "|纯小样" : "|需要扫溯源码出库");
        msg.append(checkBoxSize ? "|按箱规发货" : "").append("|").append(requirement);
        return msg.toString();
    }

    /**
     * 订单基础校验
     *
     * @param processInstanceId 流程实例ID
     * @return 有效订单信息
     * @throws BusinessException 当订单不存在或状态异常时抛出
     */
    private OrdersInfoDetailRespVO validateOrder(String processInstanceId, OrdersInfoService ordersInfoService) {
        OrdersInfoDetailRespVO ordersInfo = ordersInfoService.getOrdersInfoByPIId(processInstanceId);
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

    private String handleBusinessError(String processId, BusinessException e) {
        log.error("[流程 {}] 业务异常: {}", processId, e.getMessage(), e);
        throw e;
    }

    private void logErrorIfNeeded(String processId, String errorMsg) {
        if (StrUtil.isNotBlank(errorMsg)) {
            BizErrorLogService service = SpringUtil.getObject(BizErrorLogService.class);
            service.createBizErrorLog(LOG_MODULE, LOG_TYPE, processId, null, errorMsg);
        }
    }
}

