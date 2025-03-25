package cn.iocoder.yudao.module.fx.bpm;

import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.fx.controller.admin.utils.SpringUtil;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("instantNotifyTaskListener")
@Slf4j
public class InstantNotifyTaskListener implements TaskListener {

    /**
     * 更新系统即时通知
     *
     * @param delegateTask
     */
    @Override
    public void notify(DelegateTask delegateTask) {

        // 查找taskId
        try {
            // 获取任务处理人和流程实例ID
            String assignee = delegateTask.getAssignee();
            String processInstanceId = delegateTask.getProcessInstanceId();
            BpmProcessInstanceApi processInstanceApi = SpringUtil.getObject(BpmProcessInstanceApi.class);
            String processInstanceName = processInstanceApi.getProcessInstanceName(processInstanceId);
            if (assignee == null || processInstanceId == null) {
                log.error("更新系统即时通知异常，未找到对应的assignee或者processInstanceId");
                return;
            }

            // 获取用户详细信息
            AdminUserService adminUserService = SpringUtil.getObject(AdminUserService.class);
            AdminUserDO user = adminUserService.getUser(Long.valueOf(assignee));
            if (user == null) {
                log.error("更新系统即时通知异常，未找到对应的用户");
                return;
            }
            Long userId = 1L;
//            Long userId = user.getId();
            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("title", processInstanceName);
            NotifyMessageSendApi notifySendApi = SpringUtil.getObject(NotifyMessageSendApi.class);
            notifySendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO()
                    .setUserId(userId).setTemplateCode("notice-01").setTemplateParams(templateParams));
            log.info("更新系统即时通知成功，用户为：{}", user.getNickname());
        } catch (Exception e) {
            log.error("更新系统即时通知异常，异常信息为：{}", e.getMessage());
        }
    }
}

