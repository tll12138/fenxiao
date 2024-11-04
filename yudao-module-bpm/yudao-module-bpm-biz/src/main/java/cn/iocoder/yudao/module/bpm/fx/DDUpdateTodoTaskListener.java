package cn.iocoder.yudao.module.bpm.fx;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DDUpdateTodoTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("[execute][execution({}) 被调用！]", delegateTask.getId());
        String processInstanceId = delegateTask.getProcessInstanceId();
        log.info("[execute][execution({}) processInstanceId({}) 被调用！]", delegateTask.getId(), processInstanceId);
    }
}

