package cn.iocoder.yudao.module.fx.dal.mysql.sentmessage;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.sentmessage.SentMessageDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.sentmessage.vo.*;

/**
 * 分销发货要求消息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface SentMessageMapper extends BaseMapperX<SentMessageDO> {

    default PageResult<SentMessageDO> selectPage(SentMessagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SentMessageDO>()
                .eqIfPresent(SentMessageDO::getType, reqVO.getType())
                .eqIfPresent(SentMessageDO::getSoId, reqVO.getSoId())
                .eqIfPresent(SentMessageDO::getMsg, reqVO.getMsg())
                .eqIfPresent(SentMessageDO::getIsSend, reqVO.getIsSend())
                .betweenIfPresent(SentMessageDO::getSendTime, reqVO.getSendTime())
                .eqIfPresent(SentMessageDO::getWebhook, reqVO.getWebhook())
                .eqIfPresent(SentMessageDO::getSecret, reqVO.getSecret())
                .eqIfPresent(SentMessageDO::getWarehouseId, reqVO.getWarehouseId())
                .orderByDesc(SentMessageDO::getId));
    }

}