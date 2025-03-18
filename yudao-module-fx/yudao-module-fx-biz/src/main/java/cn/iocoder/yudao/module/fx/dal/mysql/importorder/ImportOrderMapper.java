package cn.iocoder.yudao.module.fx.dal.mysql.importorder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.importorder.ImportOrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客商代发单 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ImportOrderMapper extends BaseMapperX<ImportOrderDO> {

    default PageResult<ImportOrderDO> selectPage(ImportOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ImportOrderDO>()
                .eqIfPresent(ImportOrderDO::getProductQuantity, reqVO.getProductQuantity())
                .eqIfPresent(ImportOrderDO::getProvince, reqVO.getProvince())
                .eqIfPresent(ImportOrderDO::getCity, reqVO.getCity())
                .eqIfPresent(ImportOrderDO::getSoId, reqVO.getSoId())
                .eqIfPresent(ImportOrderDO::getSkuId, reqVO.getSkuId())
                .eqIfPresent(ImportOrderDO::getDistrict, reqVO.getDistrict())
                .eqIfPresent(ImportOrderDO::getAddress, reqVO.getAddress())
                .eqIfPresent(ImportOrderDO::getContact, reqVO.getContact())
                .eqIfPresent(ImportOrderDO::getMobile, reqVO.getMobile())
                .eqIfPresent(ImportOrderDO::getIsShipped, reqVO.getIsShipped())
                .eqIfPresent(ImportOrderDO::getIsSalesOrderGenerated, reqVO.getIsSalesOrderGenerated())
                .eqIfPresent(ImportOrderDO::getCustomerid, reqVO.getCustomerid())
                .eqIfPresent(ImportOrderDO::getWarehouseid, reqVO.getWarehouseid())
                .eqIfPresent(ImportOrderDO::getSaleno, reqVO.getSaleno())
                .eqIfPresent(ImportOrderDO::getExpressCompany, reqVO.getExpressCompany())
                .eqIfPresent(ImportOrderDO::getTrackingNumber, reqVO.getTrackingNumber())
                .eqIfPresent(ImportOrderDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ImportOrderDO::getBusinessAffiliation, reqVO.getBusinessAffiliation())
                .eqIfPresent(ImportOrderDO::getPrice, reqVO.getPrice())
                .eqIfPresent(ImportOrderDO::getIsTraceless, reqVO.getIsTraceless())
                .eqIfPresent(ImportOrderDO::getPayingDistributorId, reqVO.getPayingDistributorId())
                .eqIfPresent(ImportOrderDO::getExpressCompanyId, reqVO.getExpressCompanyId())
                .betweenIfPresent(ImportOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ImportOrderDO::getId));
    }

}