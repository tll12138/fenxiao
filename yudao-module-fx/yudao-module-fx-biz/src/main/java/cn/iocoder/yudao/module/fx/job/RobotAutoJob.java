package cn.iocoder.yudao.module.fx.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.bpm.enums.task.BpmTaskStatusEnum;
import cn.iocoder.yudao.module.fx.service.returnorder.ReturnOrderService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 机器人自动审批任务
 *
 * @author tll
 */
@Slf4j
@Component("RobotAutoJob")
public class RobotAutoJob implements JobHandler {

    @Resource
    private TaskService taskService;
    @Resource
    private ReturnOrderService returnOrderService;

    @Override
    @TenantIgnore
    public String execute(String param) {
        TaskQuery taskQuery = taskService.createTaskQuery()
                .taskAssignee("143") // 分配给机器人
                .active()
                .includeProcessVariables()
                .orderByTaskCreateTime().desc(); // 创建时间倒序
        long count = taskQuery.count();
        if (count == 0) {
            return "机器人自动审批任务";
        }
        List<Task> tasks = taskQuery.list();
        tasks.forEach(task -> {
            try {
                // 1 更新 task 状态、原因
                updateTaskStatusAndReason(task.getId(), BpmTaskStatusEnum.APPROVE.getStatus(), "机器人自动处理");
                // 2 调用 BPM complete 去完成任务
                taskService.complete(task.getId());
            } catch (Exception e) {
                log.error("[execute][任务 {} 处理失败]", task.getId(), e);
            }
        });
        log.info("[execute][机器人自动审批任务]");
        return "机器人自动审批任务";
    }

    /**
     * 更新流程任务的 status 状态、reason 理由
     *
     * @param id     任务编号
     * @param status 状态
     * @param reason 理由（审批通过、审批不通过的理由）
     */
    private void updateTaskStatusAndReason(String id, Integer status, String reason) {
        updateTaskStatus(id, status);
        taskService.setVariableLocal(id, "TASK_REASON", reason);
    }

    /**
     * 更新流程任务的 status 状态
     *
     * @param id     任务编号
     * @param status 状态
     */
    private void updateTaskStatus(String id, Integer status) {
        taskService.setVariableLocal(id, "TASK_STATUS", status);
    }

}
