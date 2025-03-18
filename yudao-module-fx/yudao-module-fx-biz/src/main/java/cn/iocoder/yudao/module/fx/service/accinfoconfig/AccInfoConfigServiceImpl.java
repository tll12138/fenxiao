package cn.iocoder.yudao.module.fx.service.accinfoconfig;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.accinfoconfig.vo.AccInfoConfigPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.accinfoconfig.vo.AccInfoConfigSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.accinfoconfig.AccInfoConfigDO;
import cn.iocoder.yudao.module.fx.dal.mysql.accinfoconfig.AccInfoConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.ACC_INFO_CONFIG_NOT_EXISTS;

/**
 * 客商账户初始化配置 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class AccInfoConfigServiceImpl implements AccInfoConfigService {

    @Resource
    private AccInfoConfigMapper accInfoConfigMapper;

    @Override
    public Integer createAccInfoConfig(AccInfoConfigSaveReqVO createReqVO) {
        // 插入
        AccInfoConfigDO accInfoConfig = BeanUtils.toBean(createReqVO, AccInfoConfigDO.class);
        accInfoConfigMapper.insert(accInfoConfig);
        // 返回
        return accInfoConfig.getId();
    }

    @Override
    public void updateAccInfoConfig(AccInfoConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateAccInfoConfigExists(updateReqVO.getId());
        // 更新
        AccInfoConfigDO updateObj = BeanUtils.toBean(updateReqVO, AccInfoConfigDO.class);
        accInfoConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteAccInfoConfig(Integer id) {
        // 校验存在
        validateAccInfoConfigExists(id);
        // 删除
        accInfoConfigMapper.deleteById(id);
    }

    private void validateAccInfoConfigExists(Integer id) {
        if (accInfoConfigMapper.selectById(id) == null) {
            throw exception(ACC_INFO_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public AccInfoConfigDO getAccInfoConfig(Integer id) {
        return accInfoConfigMapper.selectById(id);
    }

    @Override
    public PageResult<AccInfoConfigDO> getAccInfoConfigPage(AccInfoConfigPageReqVO pageReqVO) {
        return accInfoConfigMapper.selectPage(pageReqVO);
    }

    /**
     * 获得所有客商账户初始化配置
     *
     * @return
     */
    @Override
    public List<AccInfoConfigDO> getAllAccInfoConfig() {
        return accInfoConfigMapper.selectList();
    }

}