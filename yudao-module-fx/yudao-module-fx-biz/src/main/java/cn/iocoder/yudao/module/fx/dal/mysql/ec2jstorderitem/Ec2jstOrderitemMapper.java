package cn.iocoder.yudao.module.fx.dal.mysql.ec2jstorderitem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.ec2jstorderitem.vo.Ec2jstOrderitemPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorderitem.Ec2jstOrderitemDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分销订单上传详情中间 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface Ec2jstOrderitemMapper extends BaseMapperX<Ec2jstOrderitemDO> {

    default PageResult<Ec2jstOrderitemDO> selectPage(Ec2jstOrderitemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<Ec2jstOrderitemDO>()
                .eqIfPresent(Ec2jstOrderitemDO::getSkuiId, reqVO.getSkuiId())
                .eqIfPresent(Ec2jstOrderitemDO::getShopSkuId, reqVO.getShopSkuId())
                .eqIfPresent(Ec2jstOrderitemDO::getIId, reqVO.getIId())
                .eqIfPresent(Ec2jstOrderitemDO::getPic, reqVO.getPic())
                .eqIfPresent(Ec2jstOrderitemDO::getPropertiesValue, reqVO.getPropertiesValue())
                .eqIfPresent(Ec2jstOrderitemDO::getAmount, reqVO.getAmount())
                .eqIfPresent(Ec2jstOrderitemDO::getBasePrice, reqVO.getBasePrice())
                .eqIfPresent(Ec2jstOrderitemDO::getQty, reqVO.getQty())
                .likeIfPresent(Ec2jstOrderitemDO::getName, reqVO.getName())
                .eqIfPresent(Ec2jstOrderitemDO::getRefundStatus, reqVO.getRefundStatus())
                .eqIfPresent(Ec2jstOrderitemDO::getOuterOiId, reqVO.getOuterOiId())
                .eqIfPresent(Ec2jstOrderitemDO::getRemark, reqVO.getRemark())
                .eqIfPresent(Ec2jstOrderitemDO::getMainid, reqVO.getMainid())
                .orderByDesc(Ec2jstOrderitemDO::getMainid));
    }

}