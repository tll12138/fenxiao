package cn.iocoder.yudao.module.fx.service.sentmessage;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.sentmessage.vo.SentMessagePageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.sentmessage.vo.SentMessageSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.sentmessage.SentMessageDO;
import cn.iocoder.yudao.module.fx.dal.mysql.sentmessage.SentMessageMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 分销发货要求消息 Service 实现类
 *
 * @author 管理员
 */
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

}