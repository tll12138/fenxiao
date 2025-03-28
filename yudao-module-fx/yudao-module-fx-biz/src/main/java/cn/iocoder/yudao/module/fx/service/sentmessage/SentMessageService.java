package cn.iocoder.yudao.module.fx.service.sentmessage;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.sentmessage.vo.SentMessagePageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.sentmessage.vo.SentMessageSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.sentmessage.SentMessageDO;

import javax.validation.Valid;

/**
 * 分销发货要求消息 Service 接口
 *
 * @author 管理员
 */
public interface SentMessageService {

    /**
     * 创建分销发货要求消息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSentMessage(@Valid SentMessageSaveReqVO createReqVO);

    /**
     * 获得分销发货要求消息
     *
     * @param id 编号
     * @return 分销发货要求消息
     */
    SentMessageDO getSentMessage(Long id);

    /**
     * 获得分销发货要求消息分页
     *
     * @param pageReqVO 分页查询
     * @return 分销发货要求消息分页
     */
    PageResult<SentMessageDO> getSentMessagePage(SentMessagePageReqVO pageReqVO);

}