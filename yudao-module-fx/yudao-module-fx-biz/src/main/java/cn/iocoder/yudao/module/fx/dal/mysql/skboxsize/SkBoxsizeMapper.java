package cn.iocoder.yudao.module.fx.dal.mysql.skboxsize;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.skboxsize.SkBoxsizeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.skboxsize.vo.*;

/**
 * 商品箱规 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface SkBoxsizeMapper extends BaseMapperX<SkBoxsizeDO> {

    default PageResult<SkBoxsizeDO> selectPage(SkBoxsizePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SkBoxsizeDO>()
                .eqIfPresent(SkBoxsizeDO::getSkuId, reqVO.getSkuId())
                .likeIfPresent(SkBoxsizeDO::getSkuName, reqVO.getSkuName())
                .eqIfPresent(SkBoxsizeDO::getBoxSize, reqVO.getBoxSize())
                .betweenIfPresent(SkBoxsizeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SkBoxsizeDO::getId));
    }

}