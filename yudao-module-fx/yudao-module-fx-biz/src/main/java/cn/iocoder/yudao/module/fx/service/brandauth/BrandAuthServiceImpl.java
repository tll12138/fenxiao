package cn.iocoder.yudao.module.fx.service.brandauth;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.brandauth.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.brandauth.BrandAuthDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.fx.dal.mysql.brandauth.BrandAuthMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.*;

/**
 * 品牌授权 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class BrandAuthServiceImpl implements BrandAuthService {

    @Resource
    private BrandAuthMapper brandAuthMapper;

    @Override
    public Integer createBrandAuth(BrandAuthSaveReqVO createReqVO) {
        // 插入
        BrandAuthDO brandAuth = BeanUtils.toBean(createReqVO, BrandAuthDO.class);
        brandAuthMapper.insert(brandAuth);
        // 返回
        return brandAuth.getId();
    }

    @Override
    public void updateBrandAuth(BrandAuthSaveReqVO updateReqVO) {
        // 校验存在
        validateBrandAuthExists(updateReqVO.getId());
        // 更新
        BrandAuthDO updateObj = BeanUtils.toBean(updateReqVO, BrandAuthDO.class);
        brandAuthMapper.updateById(updateObj);
    }

    @Override
    public void deleteBrandAuth(Integer id) {
        // 校验存在
        validateBrandAuthExists(id);
        // 删除
        brandAuthMapper.deleteById(id);
    }

    private void validateBrandAuthExists(Integer id) {
        if (brandAuthMapper.selectById(id) == null) {
            throw exception(BRAND_AUTH_NOT_EXISTS);
        }
    }

    @Override
    public BrandAuthDO getBrandAuth(Integer id) {
        return brandAuthMapper.selectById(id);
    }

    @Override
    public PageResult<BrandAuthDO> getBrandAuthPage(BrandAuthPageReqVO pageReqVO) {
        return brandAuthMapper.selectPage(pageReqVO);
    }

}