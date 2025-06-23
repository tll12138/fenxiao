package cn.iocoder.yudao.module.fx.dal.mysql.payaccount;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.payaccount.vo.PayAccountPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.payaccount.PayAccountDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分销支付账户 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PayAccountMapper extends BaseMapperX<PayAccountDO> {

    default PageResult<PayAccountDO> selectPage(PayAccountPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayAccountDO>()
                .eqIfPresent(PayAccountDO::getPayType, reqVO.getPayType())
                .eqIfPresent(PayAccountDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(PayAccountDO::getCustomerName, reqVO.getCustomerName())
                .eqIfPresent(PayAccountDO::getAccountNo, reqVO.getAccountNo())
                .eqIfPresent(PayAccountDO::getDescription, reqVO.getDescription())
                .eqIfPresent(PayAccountDO::getIsActive, reqVO.getIsActive())
                .betweenIfPresent(PayAccountDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayAccountDO::getId));
    }

}