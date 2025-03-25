package cn.iocoder.yudao.module.bpm.api.task;

import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCancelReqVO;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Flowable 流程实例 Api 实现类
 *
 * @author 芋道源码
 * @author jason
 */
@Service
@Validated
public class BpmProcessInstanceApiImpl implements BpmProcessInstanceApi {

    @Resource
    private BpmProcessInstanceService processInstanceService;

    @Override
    public String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqDTO reqDTO) {
        return processInstanceService.createProcessInstance(userId, reqDTO);
    }

    @Override
    public void cancelProcessInstance(Long userId, String processInstanceId, String reason) {
        BpmProcessInstanceCancelReqVO bpmProcessInstanceCancelReqVO = new BpmProcessInstanceCancelReqVO();
        bpmProcessInstanceCancelReqVO.setId(processInstanceId);
        bpmProcessInstanceCancelReqVO.setReason(reason);
        processInstanceService.cancelProcessInstanceByStartUser(userId, bpmProcessInstanceCancelReqVO);
    }

    /**
     * 获得流程实例名称
     *
     * @param processInstanceId 流程实例的编号
     */
    @Override
    public String getProcessInstanceName(String processInstanceId) {
        return processInstanceService.getProcessInstance(processInstanceId).getName();
    }
}
