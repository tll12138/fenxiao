package cn.iocoder.yudao.module.fx.service.sentmessage;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.http.HttpRequest;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.sentmessage.vo.SentMessagePageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.sentmessage.vo.SentMessageSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.sentmessage.SentMessageDO;
import cn.iocoder.yudao.module.fx.dal.mysql.sentmessage.SentMessageMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 分销发货要求消息 Service 实现类
 *
 * @author 管理员
 */
@Slf4j
@Service
@Validated
public class SentMessageServiceImpl implements SentMessageService {

    @Resource
    private SentMessageMapper sentMessageMapper;

    @Override
    public Long createSentMessage(SentMessageSaveReqVO createReqVO) {
        // 插入
        SentMessageDO sentMessage = BeanUtils.toBean(createReqVO, SentMessageDO.class);
        sentMessageMapper.insert(sentMessage);
        // 返回
        return sentMessage.getId();
    }

    @Override
    public SentMessageDO getSentMessage(Long id) {
        return sentMessageMapper.selectById(id);
    }

    @Override
    public PageResult<SentMessageDO> getSentMessagePage(SentMessagePageReqVO pageReqVO) {
        return sentMessageMapper.selectPage(pageReqVO);
    }

    /**
     * 执行发送消息
     * id为空执行全部，id不为空执行单条
     *
     * @param id
     */
    @Override
    public void executeSendMsg(Long id) {
        List<Long> targetIds = new ArrayList<>();
        if (ObjUtil.isNotNull(id)) {
            SentMessageDO message = sentMessageMapper.selectById(id);
            if (message == null || message.getIsSend() == 1) {
                log.warn("[executeSendMsg] 消息不存在或已发送，ID: {}", id);
                return;
            }
            targetIds.add(id);
        } else { // 处理未发送消息
            targetIds = sentMessageMapper.getNotSentMsgId();
            if (ObjUtil.isEmpty(targetIds)) {
                log.info("[executeSendMsg] 没有需要处理的发货消息推送记录");
                return;
            }
        }
        log.info("[executeSendMsg] 开始处理发货消息，数量: {}", targetIds.size());
        JSONObject paramsJson = getJsonObject(targetIds);
        log.info("[executeSendMsg] paramsJson: {}", paramsJson);
        String response = HttpRequest.post("https://oa.puqi.group/oa/yd-job-start")
                .body(paramsJson.toJSONString())
                .timeout(100000)
                .execute()
                .body();
        JSONObject responseJson = JSON.parseObject(response);
        if (!"0".equals(responseJson.getString("code"))) {
            log.error("[executeSendMsg] 接口调用失败: {}", responseJson.getString("msg"));
            return;
        }
        // 更新发送状态
        sentMessageMapper.update(new UpdateWrapper<SentMessageDO>()
                .set("is_send", 1)
                .in("id", targetIds));
        log.info("[executeSendMsg] 成功处理 {} 条发货通知", targetIds.size());
    }

    private static JSONObject getJsonObject(List<Long> targetIds) {
        JSONArray paramsList = new JSONArray();

        JSONObject idParam = new JSONObject();
        idParam.put("name", "ids");
        idParam.put("value", StringUtils.join(targetIds, ","));
        idParam.put("type", "str");
        paramsList.add(idParam);

        JSONObject paramsJson = new JSONObject();
        paramsJson.put("uuid", "bca49a49-3951-4ac1-a8f6-041b94599ebb");
        paramsJson.put("group_uuid", "bb7d45e7-874a-424e-9966-c9b7c3419275");
        paramsJson.put("params", JSON.toJSONString(paramsList));
        return paramsJson;
    }

}