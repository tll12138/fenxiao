package cn.iocoder.yudao.module.fx.bpm;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.fx.controller.admin.amountadj.vo.AmountAdjSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ordersinfo.vo.OrdersInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.utils.SpringUtil;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.module.fx.enums.AmountAdjType;
import cn.iocoder.yudao.module.fx.enums.BooleanType;
import cn.iocoder.yudao.module.fx.enums.OrderStatusType;
import cn.iocoder.yudao.module.fx.service.amountadj.AmountAdjService;
import cn.iocoder.yudao.module.fx.service.bizerrorlog.BizErrorLogService;
import cn.iocoder.yudao.module.fx.service.customeraccount.CustomerAccountService;
import cn.iocoder.yudao.module.fx.service.ordersinfo.OrdersInfoService;
import cn.iocoder.yudao.module.fx.utils.BigDecimalUtils;
import com.diboot.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Component("salesOrderAuditTaskListener")
@Slf4j
public class SalesOrderAuditTaskListener {

    /**
     * 销售单审核节点后动作
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
            // 审核通过逻辑
            CustomerAccountService accountService = SpringUtil.getObject(CustomerAccountService.class);
            CustomerAccountDO account = validateAccount(ordersInfo, Long.getLong(processInstanceId), accountService);
            processApproveAction(ordersInfo, account, accountService, ordersInfoService);
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
                bizErrorLogService.createBizErrorLog("fx", "SalesOrderAuditTaskListener", processInstanceId, null, errorMsg);
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

    /**
     * 账户有效性校验
     *
     * @param ordersInfo        订单信息
     * @param processInstanceId 流程实例ID（用于日志追踪）
     * @param accountService    账户服务实例
     * @return 有效账户对象
     * @throws BusinessException 当账户不存在或已冻结时抛出
     */
    private CustomerAccountDO validateAccount(OrdersInfoDetailRespVO ordersInfo, Long processInstanceId, CustomerAccountService accountService) {
        // 根据业务主体获取账户
        int company = ordersInfo.getReceiveSupplierId().intValue();
        CustomerAccountDO account = accountService.getCustomerAccountByDistributorIdAndCompany(
                ordersInfo.getDistributorId(), company);

        // 账户存在性检查
        if (account == null && BigDecimalUtils.eq(ordersInfo.getSalesAmount(), BigDecimal.ZERO)) {
            // 当金额为0时，创建初始账户
            accountService.createSingleCustomerAccount(processInstanceId, company, ordersInfo.getDistributorName());
        } else if (account == null) {
            throw new BusinessException(StrUtil.format("流程[{}]未找到分销商扣款账户", processInstanceId));
        }
        // 账户状态检查（冻结状态拦截）
        if (account != null && BooleanType.YES.getType().equals(account.getIsActive())) {
            throw new BusinessException(StrUtil.format("账户[{}]已冻结", account.getId()));
        }
        return account;
    }

    private void processApproveAction(OrdersInfoDetailRespVO ordersInfo, CustomerAccountDO account, CustomerAccountService accountService, OrdersInfoService ordersInfoService) {
        BigDecimal salesAmount = ordersInfo.getSalesAmount();
        BigDecimal balance = account.getBalance();
        boolean allowOverdraft = BooleanType.YES.getType().equals(account.getIsAllow());

        // 统一金额处理逻辑
        if (BigDecimalUtils.gt(salesAmount, balance) && !allowOverdraft) {
            throw new BusinessException(StrUtil.format("账户[{}]余额不足且不允许超额", account.getId()));
        }

        updateAccountBalance(account, salesAmount, accountService);
        createAmountAdjustRecord(account, salesAmount, ordersInfo.getOrderId(), ordersInfo.getRemark());
        ordersInfoService.updateOrdersInfoStatus(ordersInfo.getId(), OrderStatusType.WAITING_FOR_ERP.getType());
    }

    private void updateAccountBalance(CustomerAccountDO account, BigDecimal salesAmount, CustomerAccountService accountService) {
        account.setBalance(BigDecimalUtils.subtract(account.getBalance(), salesAmount));
        account.setDetainAmount(salesAmount);
        account.setUpdater(Objects.requireNonNull(getLoginUserId()).toString());
        account.setUpdateTime(LocalDateTime.now());
        accountService.updateCustomerAccountByDO(account);
    }

    private void createAmountAdjustRecord(CustomerAccountDO account, BigDecimal salesAmount,
                                          String orderId, String remark) {
        AmountAdjService amountAdjService = SpringUtil.getObject(AmountAdjService.class);
        amountAdjService.createAmountAdj(new AmountAdjSaveReqVO()
                .setAmount(salesAmount)
                .setAccount(account.getId().toString())
                .setRemark(remark)
                .setSoId(orderId)
                .setOrderDate(DateUtil.now())
                .setType(AmountAdjType.WITHHOLD_ADJUST.getType())
                .setAdjustWithholdBalance(account.getDetainAmount()));
    }
}

