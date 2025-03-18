package cn.iocoder.yudao.module.fx.dal.mysql.billapply;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplyPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.billapply.BillApplyDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 发票申请 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface BillApplyMapper extends BaseMapperX<BillApplyDO> {

    default PageResult<BillApplyDO> selectPage(BillApplyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BillApplyDO>()
                .eqIfPresent(BillApplyDO::getApplyMan, reqVO.getApplyMan())
                .eqIfPresent(BillApplyDO::getApplyDate, reqVO.getApplyDate())
                .eqIfPresent(BillApplyDO::getBillDate, reqVO.getBillDate())
                .eqIfPresent(BillApplyDO::getAddress, reqVO.getAddress())
                .eqIfPresent(BillApplyDO::getBankNo, reqVO.getBankNo())
                .eqIfPresent(BillApplyDO::getAmount, reqVO.getAmount())
                .eqIfPresent(BillApplyDO::getMaker, reqVO.getMaker())
                .eqIfPresent(BillApplyDO::getRemark, reqVO.getRemark())
                .eqIfPresent(BillApplyDO::getBillType, reqVO.getBillType())
                .eqIfPresent(BillApplyDO::getTotalAmount, reqVO.getTotalAmount())
                .eqIfPresent(BillApplyDO::getSaleOrder, reqVO.getSaleOrder())
                .eqIfPresent(BillApplyDO::getBillHead, reqVO.getBillHead())
                .eqIfPresent(BillApplyDO::getDocument, reqVO.getDocument())
                .eqIfPresent(BillApplyDO::getFinancialStatement, reqVO.getFinancialStatement())
                .likeIfPresent(BillApplyDO::getPurchaserName, reqVO.getPurchaserName())
                .eqIfPresent(BillApplyDO::getTaxNo, reqVO.getTaxNo())
                .likeIfPresent(BillApplyDO::getCusName, reqVO.getCusName())
                .eqIfPresent(BillApplyDO::getBillInfo, reqVO.getBillInfo())
                .eqIfPresent(BillApplyDO::getRid, reqVO.getRid())
                .eqIfPresent(BillApplyDO::getIsOver, reqVO.getIsOver())
                .eqIfPresent(BillApplyDO::getIsYj, reqVO.getIsYj())
                .eqIfPresent(BillApplyDO::getEmail, reqVO.getEmail())
                .eqIfPresent(BillApplyDO::getEmailId, reqVO.getEmailId())
                .eqIfPresent(BillApplyDO::getIsSend, reqVO.getIsSend())
                .orderByDesc(BillApplyDO::getId));
    }

}