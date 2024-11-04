package cn.iocoder.yudao.module.fx.service.subcompanyinfo;

import cn.iocoder.yudao.module.fx.convert.CustomerCovert;
import com.diboot.core.service.impl.BaseServiceImpl;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.subcompanyinfo.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.subcompanyinfo.SubCompanyInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.subcompanyinfo.SubCompanyInfoMapper;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 子公司信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class SubCompanyInfoServiceImpl extends MPJBaseServiceImpl<SubCompanyInfoMapper, SubCompanyInfoDO> implements SubCompanyInfoService {

    @Resource
    private SubCompanyInfoMapper subCompanyInfoMapper;

    @Override
    public Long createSubCompanyInfo(SubCompanyInfoSaveReqVO createReqVO) {
        // 插入
        SubCompanyInfoDO subCompanyInfo = BeanUtils.toBean(createReqVO, SubCompanyInfoDO.class);
        subCompanyInfoMapper.insert(subCompanyInfo);
        // 返回
        return subCompanyInfo.getId();
    }

    @Override
    public void updateSubCompanyInfo(SubCompanyInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateSubCompanyInfoExists(updateReqVO.getId());
        // 更新
        SubCompanyInfoDO updateObj = BeanUtils.toBean(updateReqVO, SubCompanyInfoDO.class);
        subCompanyInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteSubCompanyInfo(Long id) {
        // 校验存在
        validateSubCompanyInfoExists(id);
        // 删除
        subCompanyInfoMapper.deleteById(id);
    }

    private void validateSubCompanyInfoExists(Long id) {
        if (subCompanyInfoMapper.selectById(id) == null) {
            throw exception(SUB_COMPANY_INFO_NOT_EXISTS);
        }
    }

    @Override
    public SubCompanyInfoDO getSubCompanyInfo(Long id) {
        return subCompanyInfoMapper.selectById(id);
    }

    @Override
    public PageResult<SubCompanyInfoDO> getSubCompanyInfoPage(SubCompanyInfoPageReqVO pageReqVO) {
        return subCompanyInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SubCompanyInfoRespVO> getSubCompanyInfoList() {
        List<SubCompanyInfoDO> subCompanyInfoDOS = subCompanyInfoMapper.selectList();
        return CustomerCovert.INSTANCE.convertSubCompanyList(subCompanyInfoDOS);
    }

}