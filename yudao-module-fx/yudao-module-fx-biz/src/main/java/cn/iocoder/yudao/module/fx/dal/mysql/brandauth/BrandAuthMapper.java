package cn.iocoder.yudao.module.fx.dal.mysql.brandauth;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.brandauth.BrandAuthDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.brandauth.vo.*;

/**
 * 品牌授权 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface BrandAuthMapper extends BaseMapperX<BrandAuthDO> {

    default PageResult<BrandAuthDO> selectPage(BrandAuthPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BrandAuthDO>()
                .eqIfPresent(BrandAuthDO::getIp, reqVO.getIp())
                .eqIfPresent(BrandAuthDO::getUuid, reqVO.getUuid())
                .eqIfPresent(BrandAuthDO::getClientDevice, reqVO.getClientDevice())
                .eqIfPresent(BrandAuthDO::getClientOs, reqVO.getClientOs())
                .eqIfPresent(BrandAuthDO::getClientBrowser, reqVO.getClientBrowser())
                .eqIfPresent(BrandAuthDO::getRemark, reqVO.getRemark())
                .likeIfPresent(BrandAuthDO::getStoreName, reqVO.getStoreName())
                .eqIfPresent(BrandAuthDO::getStoreId, reqVO.getStoreId())
                .eqIfPresent(BrandAuthDO::getStoreUrl, reqVO.getStoreUrl())
                .eqIfPresent(BrandAuthDO::getEmail, reqVO.getEmail())
                .betweenIfPresent(BrandAuthDO::getApplyDate, reqVO.getApplyDate())
                .betweenIfPresent(BrandAuthDO::getApplyDatetime, reqVO.getApplyDatetime())
                .eqIfPresent(BrandAuthDO::getIsSee, reqVO.getIsSee())
                .eqIfPresent(BrandAuthDO::getChannel, reqVO.getChannel())
                .eqIfPresent(BrandAuthDO::getWorkflowid, reqVO.getWorkflowid())
                .eqIfPresent(BrandAuthDO::getIsOnline, reqVO.getIsOnline())
                .eqIfPresent(BrandAuthDO::getBrand, reqVO.getBrand())
                .orderByDesc(BrandAuthDO::getId));
    }

}