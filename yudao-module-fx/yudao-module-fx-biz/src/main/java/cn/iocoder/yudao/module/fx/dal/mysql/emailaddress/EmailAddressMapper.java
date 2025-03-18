package cn.iocoder.yudao.module.fx.dal.mysql.emailaddress;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.fx.dal.dataobject.emailaddress.EmailAddressDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.fx.controller.admin.emailaddress.vo.*;

/**
 * 发票邮箱库 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface EmailAddressMapper extends BaseMapperX<EmailAddressDO> {

    default PageResult<EmailAddressDO> selectPage(EmailAddressPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EmailAddressDO>()
                .eqIfPresent(EmailAddressDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(EmailAddressDO::getEmail, reqVO.getEmail())
                .eqIfPresent(EmailAddressDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(EmailAddressDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(EmailAddressDO::getCompany, reqVO.getCompany())
                .eqIfPresent(EmailAddressDO::getTax, reqVO.getTax())
                .eqIfPresent(EmailAddressDO::getBank, reqVO.getBank())
                .eqIfPresent(EmailAddressDO::getAddress, reqVO.getAddress())
                .eqIfPresent(EmailAddressDO::getIsActive, reqVO.getIsActive())
                .orderByDesc(EmailAddressDO::getId));
    }

}