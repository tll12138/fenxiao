package cn.iocoder.yudao.module.fx.service.monsettlement;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.monsettlement.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.monsettlement.MonSettlementDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.monsettlement.MonSettlementMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 分销账户月结 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class MonSettlementServiceImpl implements MonSettlementService {

    @Resource
    private MonSettlementMapper monSettlementMapper;

    @Override
    public Integer createMonSettlement(MonSettlementSaveReqVO createReqVO) {
        // 插入
        MonSettlementDO monSettlement = BeanUtils.toBean(createReqVO, MonSettlementDO.class);
        monSettlementMapper.insert(monSettlement);
        // 返回
        return monSettlement.getId();
    }

    @Override
    public void updateMonSettlement(MonSettlementSaveReqVO updateReqVO) {
        // 校验存在
        validateMonSettlementExists(updateReqVO.getId());
        // 更新
        MonSettlementDO updateObj = BeanUtils.toBean(updateReqVO, MonSettlementDO.class);
        monSettlementMapper.updateById(updateObj);
    }

    @Override
    public void deleteMonSettlement(Integer id) {
        // 校验存在
        validateMonSettlementExists(id);
        // 删除
        monSettlementMapper.deleteById(id);
    }

    private void validateMonSettlementExists(Integer id) {
        if (monSettlementMapper.selectById(id) == null) {
            throw exception(MON_SETTLEMENT_NOT_EXISTS);
        }
    }

    @Override
    public MonSettlementDO getMonSettlement(Integer id) {
        return monSettlementMapper.selectById(id);
    }

    @Override
    public PageResult<MonSettlementDO> getMonSettlementPage(MonSettlementPageReqVO pageReqVO) {
        return monSettlementMapper.selectPage(pageReqVO);
    }

}