package cn.iocoder.yudao.module.fx.bpm;

import cn.iocoder.yudao.module.fx.constant.FieldConstant;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.utils.SpringUtil;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.service.ordersinfo.OrdersInfoService;
import cn.iocoder.yudao.module.fx.utils.BigDecimalUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("salesOrderAuditTaskListener")
@Slf4j
public class SalesOrderAuditTaskListener {

    /**
     * 销售单审核节点后动作
     */
    public void execute(DelegateExecution delegateExecution, String roleType) {
        // 查找taskId
        try {
            String processInstanceId = delegateExecution.getProcessInstanceId();
            OrdersInfoService ordersInfoService = SpringUtil.getObject(OrdersInfoService.class);
            OrdersInfoDetailRespVO ordersInfo = ordersInfoService.getOrdersInfo(processInstanceId);
            if (ordersInfo == null) {
                log.error("流程对应的销售单不存在");
                return;
            }
            if (FieldConstant.PRODUCT_POSITION.equals(roleType)) {
                if ("approve".equals(delegateExecution.getEventName())) {
                    BigDecimal salesAmount = ordersInfo.getSalesAmount();
                    boolean needsAccountCheck = BigDecimalUtils.gt(salesAmount, BigDecimal.ZERO);
                    ordersInfoService.updateOrdersInfoStatus(ordersInfo.getId(), needsAccountCheck ? OrderStatusType.ACCOUNT_NO_MONEY.getType() : OrderStatusType.FINANCE_AUDITING.getType());
                    // 审核通过逻辑
                } else if ("reject".equals(delegateExecution.getEventName())) {
                    // 审核驳回逻辑
                }
            }
        } catch (Exception e) {
            log.error("销售单审核节点后动作异常，异常信息为：{}", e.getMessage());
        }
    }
}

