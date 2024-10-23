package cn.iocoder.yudao.module.fx.dal.mysql.subcompanyinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.subcompanyinfo.SubCompanyInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.subcompanyinfo.vo.*;

/**
 * 子公司信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface SubCompanyInfoMapper extends BaseMapperX<SubCompanyInfoDO> {

    default PageResult<SubCompanyInfoDO> selectPage(SubCompanyInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SubCompanyInfoDO>()
                .likeIfPresent(SubCompanyInfoDO::getCompanyName, reqVO.getCompanyName())
                .likeIfPresent(SubCompanyInfoDO::getIdentifyId, reqVO.getIdentifyId())
                .likeIfPresent(SubCompanyInfoDO::getBank, reqVO.getBank())
                .eqIfPresent(SubCompanyInfoDO::getRegion, reqVO.getRegion())
                .orderByDesc(SubCompanyInfoDO::getId));
    }

}