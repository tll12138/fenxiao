package cn.iocoder.yudao.module.fx.service.skboxsize;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.skboxsize.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.skboxsize.SkBoxsizeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.skboxsize.SkBoxsizeMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 商品箱规 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class SkBoxsizeServiceImpl implements SkBoxsizeService {

    @Resource
    private SkBoxsizeMapper skBoxsizeMapper;

    @Override
    public Long createSkBoxsize(SkBoxsizeSaveReqVO createReqVO) {
        // 插入
        SkBoxsizeDO skBoxsize = BeanUtils.toBean(createReqVO, SkBoxsizeDO.class);
        skBoxsizeMapper.insert(skBoxsize);
        // 返回
        return skBoxsize.getId();
    }

    @Override
    public void updateSkBoxsize(SkBoxsizeSaveReqVO updateReqVO) {
        // 校验存在
        validateSkBoxsizeExists(updateReqVO.getId());
        // 更新
        SkBoxsizeDO updateObj = BeanUtils.toBean(updateReqVO, SkBoxsizeDO.class);
        skBoxsizeMapper.updateById(updateObj);
    }

    @Override
    public void deleteSkBoxsize(Long id) {
        // 校验存在
        validateSkBoxsizeExists(id);
        // 删除
        skBoxsizeMapper.deleteById(id);
    }

    private void validateSkBoxsizeExists(Long id) {
        if (skBoxsizeMapper.selectById(id) == null) {
            throw exception(SK_BOXSIZE_NOT_EXISTS);
        }
    }

    @Override
    public SkBoxsizeDO getSkBoxsize(Long id) {
        return skBoxsizeMapper.selectById(id);
    }

    @Override
    public PageResult<SkBoxsizeDO> getSkBoxsizePage(SkBoxsizePageReqVO pageReqVO) {
        return skBoxsizeMapper.selectPage(pageReqVO);
    }

}