package cn.iocoder.yudao.module.fx.service.sendrepository;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository.SendRepositoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.sendrepository.SendRepositoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * FX 发货仓库 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class SendRepositoryServiceImpl implements SendRepositoryService {

    @Resource
    private SendRepositoryMapper sendRepositoryMapper;

    @Override
    public Integer createSendRepository(SendRepositorySaveReqVO createReqVO) {
        // 插入
        SendRepositoryDO sendRepository = BeanUtils.toBean(createReqVO, SendRepositoryDO.class);
        sendRepositoryMapper.insert(sendRepository);
        // 返回
        return sendRepository.getId();
    }

    @Override
    public void updateSendRepository(SendRepositorySaveReqVO updateReqVO) {
        // 校验存在
        validateSendRepositoryExists(updateReqVO.getId());
        // 更新
        SendRepositoryDO updateObj = BeanUtils.toBean(updateReqVO, SendRepositoryDO.class);
        sendRepositoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteSendRepository(Integer id) {
        // 校验存在
        validateSendRepositoryExists(id);
        // 删除
        sendRepositoryMapper.deleteById(id);
    }

    private void validateSendRepositoryExists(Integer id) {
        if (sendRepositoryMapper.selectById(id) == null) {
            throw exception(SEND_REPOSITORY_NOT_EXISTS);
        }
    }

    @Override
    public SendRepositoryDO getSendRepository(Integer id) {
        return sendRepositoryMapper.selectById(id);
    }

    @Override
    public PageResult<SendRepositoryDO> getSendRepositoryPage(SendRepositoryPageReqVO pageReqVO) {
        return sendRepositoryMapper.selectPage(pageReqVO);
    }

}