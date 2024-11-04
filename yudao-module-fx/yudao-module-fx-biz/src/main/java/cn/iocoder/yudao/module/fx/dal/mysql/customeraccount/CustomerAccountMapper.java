package cn.iocoder.yudao.module.fx.dal.mysql.customeraccount;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 分销商账号 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface CustomerAccountMapper extends BaseMapperX<CustomerAccountDO> {

    default PageResult<CustomerAccountDO> selectPage(CustomerAccountPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CustomerAccountDO>()
                .eqIfPresent(CustomerAccountDO::getDistributorId, reqVO.getDistributorId())
                .eqIfPresent(CustomerAccountDO::getCompany, reqVO.getCompany())
                .eqIfPresent(CustomerAccountDO::getBalance, reqVO.getBalance())
                .eqIfPresent(CustomerAccountDO::getAccountId, reqVO.getAccountId())
                .eqIfPresent(CustomerAccountDO::getDetainAmount, reqVO.getDetainAmount())
                .eqIfPresent(CustomerAccountDO::getIsActive, reqVO.getIsActive())
                .eqIfPresent(CustomerAccountDO::getDeposit, reqVO.getDeposit())
                .betweenIfPresent(CustomerAccountDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CustomerAccountDO::getId));
    }

    default List<CustomerAccountDO> selectListById(Long id){
        return selectList(new LambdaQueryWrapperX<CustomerAccountDO>()
                .eqIfPresent(CustomerAccountDO::getId, id)
                .orderByDesc(CustomerAccountDO::getCreateTime));
    };
}