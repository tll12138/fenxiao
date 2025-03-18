package cn.iocoder.yudao.module.fx.dal.mysql.sendrepository;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo.SendRepositoryPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository.SendRepositoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * FX 发货仓库 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface SendRepositoryMapper extends BaseMapperX<SendRepositoryDO> {

    default PageResult<SendRepositoryDO> selectPage(SendRepositoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SendRepositoryDO>()
                .likeIfPresent(SendRepositoryDO::getName, reqVO.getName())
                .eqIfPresent(SendRepositoryDO::getType, reqVO.getType())
                .likeIfPresent(SendRepositoryDO::getAllName, reqVO.getAllName())
                .eqIfPresent(SendRepositoryDO::getIsToErp, reqVO.getIsToErp())
                .eqIfPresent(SendRepositoryDO::getIsUsed, "1")
                .orderByDesc(SendRepositoryDO::getId));
    }

}