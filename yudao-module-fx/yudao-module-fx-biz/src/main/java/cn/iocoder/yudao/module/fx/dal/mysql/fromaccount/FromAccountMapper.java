package cn.iocoder.yudao.module.fx.dal.mysql.fromaccount;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.fromaccount.vo.FromAccountPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.fromaccount.FromAccountDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分销打款账户 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface FromAccountMapper extends BaseMapperX<FromAccountDO> {

    default PageResult<FromAccountDO> selectPage(FromAccountPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FromAccountDO>()
                .eqIfPresent(FromAccountDO::getAccount, reqVO.getAccount())
                .eqIfPresent(FromAccountDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(FromAccountDO::getCustomerName, reqVO.getCustomerName())
                .eqIfPresent(FromAccountDO::getAccountType, reqVO.getAccountType())
                .eqIfPresent(FromAccountDO::getRemark, reqVO.getRemark())
                .eqIfPresent(FromAccountDO::getIsActive, reqVO.getIsActive())
                .eqIfPresent(FromAccountDO::getTotalNum, reqVO.getTotalNum())
                .eqIfPresent(FromAccountDO::getTotalAmt, reqVO.getTotalAmt())
                .likeIfPresent(FromAccountDO::getAccountName, reqVO.getAccountName())
                .eqIfPresent(FromAccountDO::getAccountId, reqVO.getAccountId())
                .betweenIfPresent(FromAccountDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FromAccountDO::getId));
    }

}