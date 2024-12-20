package cn.iocoder.yudao.module.fx.bpm;

import cn.iocoder.yudao.module.system.dal.dataobject.sms.SmsLogDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.sms.SmsLogMapper;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.system.util.dd.DingTalkUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class DDUpdateTodoTaskListener implements TaskListener {

    @Resource
    private SmsLogMapper smsLogMapper;

    @Resource
    private DingTalkUtils dingTalkUtils;

    @Resource
    private AdminUserService adminUserService;


    /**
     * 更新代办状态
     * @param delegateTask
     */
    @Override
    public void notify(DelegateTask delegateTask) {

        // 查找taskId
        try{
            String assignee = delegateTask.getAssignee();   
            String processInstanceId = delegateTask.getProcessInstanceId();
            if (assignee == null || processInstanceId == null){
                log.error("更新待办状态异常，未找到对应的assignee或者processInstanceId");
                return;
            }
            LambdaQueryWrapper<SmsLogDO> queryWrapper = Wrappers.lambdaQuery(SmsLogDO.class)
                    .eq(SmsLogDO::getProcessInstanceId, processInstanceId)
                    .eq(SmsLogDO::getUserId, assignee);
            SmsLogDO smsLogDOS = smsLogMapper.selectOne(queryWrapper);
            if (smsLogDOS == null){
                log.error("更新待办状态异常，未找到对应的短信记录");
                return;
            }

            String taskId = smsLogDOS.getApiSerialNo();
            AdminUserDO user = adminUserService.getUser(Long.valueOf(assignee));
            if (user == null){
                log.error("更新待办状态异常，未找到对应的用户");
                return;
            }
            String username = user.getUsername();
            String unionId = dingTalkUtils.getUnionId(username);
            dingTalkUtils.updateTodoTask(unionId, taskId);
            log.info("更新待办状态成功，用户为：{}，taskId为：{}",user.getNickname(), taskId);
        }catch (Exception e){
            log.error("更新待办状态异常，异常信息为：{}", e.getMessage());
        }
    }
}

