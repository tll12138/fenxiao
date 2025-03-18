package cn.iocoder.yudao.module.fx.dal.mysql.billapply;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.billapply.BillApplyDetailDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 发票申请详情 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface BillApplyDetailMapper extends BaseMapperX<BillApplyDetailDO> {

    default List<BillApplyDetailDO> selectListByMainId(Integer mainId) {
        return selectList(BillApplyDetailDO::getMainId, mainId);
    }

    default int deleteByMainId(Integer mainId) {
        return delete(BillApplyDetailDO::getMainId, mainId);
    }

}