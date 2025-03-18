package cn.iocoder.yudao.module.fx.dal.mysql.accinfoconfig;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.accinfoconfig.AccInfoConfigDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.accinfoconfig.vo.*;

/**
 * 客商账户初始化配置 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface AccInfoConfigMapper extends BaseMapperX<AccInfoConfigDO> {

    default PageResult<AccInfoConfigDO> selectPage(AccInfoConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AccInfoConfigDO>()
                .eqIfPresent(AccInfoConfigDO::getYwzt, reqVO.getYwzt())
                .eqIfPresent(AccInfoConfigDO::getAbout, reqVO.getAbout())
                .eqIfPresent(AccInfoConfigDO::getBrandid, reqVO.getBrandid())
                .betweenIfPresent(AccInfoConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AccInfoConfigDO::getId));
    }

}