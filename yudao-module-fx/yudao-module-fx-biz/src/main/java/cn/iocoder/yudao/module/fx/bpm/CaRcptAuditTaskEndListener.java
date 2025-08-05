package cn.iocoder.yudao.module.fx.bpm;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.fx.controller.admin.utils.SpringUtil;
import cn.iocoder.yudao.module.fx.dal.dataobject.carcptaud.CaRcptAudDO;
import cn.iocoder.yudao.module.fx.service.bizerrorlog.BizErrorLogService;
import cn.iocoder.yudao.module.fx.service.carcptaud.CaRcptAudService;
import com.diboot.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 分销商账户收款单归档后动作
 */
@Component("caRcptAuditTaskEndListener")
@Slf4j
public class CaRcptAuditTaskEndListener {

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
            CaRcptAudService caRcptAudService = SpringUtil.getObject(CaRcptAudService.class);
            CaRcptAudDO caRcptAudDO = validateOrder(processId, caRcptAudService);
            //校验打款账户是否存在，不存在新增，存在就更新打款账号累计次数与金额
            caRcptAudService.updateOrInsertPayAcc(caRcptAudDO);
            //更新供应商账户余额

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
     * 审批单基础校验
     *
     * @param processInstanceId 流程实例ID
     * @return 有效订单信息
     * @throws BusinessException 当订单不存在或状态异常时抛出
     */
    private CaRcptAudDO validateOrder(String processInstanceId, CaRcptAudService caRcptAudService) {
        CaRcptAudDO audDO = caRcptAudService.getInfoByPIId(processInstanceId);
        // 订单存在性校验
        if (audDO == null) {
            throw new BusinessException("收款单不存在");
        }
        return audDO;
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

