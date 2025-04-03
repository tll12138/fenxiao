package cn.iocoder.yudao.module.fx.dal.mysql.jstorderout;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.fx.controller.admin.jstorderout.vo.JstOrderOutPageReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstorderout.JstOrderOutDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstorderout.JstOrderOutDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 聚水潭发货回传中间表 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface JstOrderOutMapper extends BaseMapperX<JstOrderOutDO> {

    default PageResult<JstOrderOutDO> selectPage(JstOrderOutPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<JstOrderOutDO>()
                .eqIfPresent(JstOrderOutDO::getShopId, reqVO.getShopId())
                .eqIfPresent(JstOrderOutDO::getOId, reqVO.getOId())
                .eqIfPresent(JstOrderOutDO::getSoId, reqVO.getSoId())
                .likeIfPresent(JstOrderOutDO::getExpressName, reqVO.getExpressName())
                .eqIfPresent(JstOrderOutDO::getExpress, reqVO.getExpress())
                .eqIfPresent(JstOrderOutDO::getExpressCode, reqVO.getExpressCode())
                .eqIfPresent(JstOrderOutDO::getIsTran, reqVO.getIsTran())
                .eqIfPresent(JstOrderOutDO::getModified, reqVO.getModified())
                .betweenIfPresent(JstOrderOutDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(JstOrderOutDO::getId));
    }

    /**
     * 根据内部单号创建或更新聚水潭发货回传中间表
     *
     * @param jstOrderOutDO
     * @return
     */
    int mergeOrderOut(JstOrderOutDO jstOrderOutDO);

    /**
     * 获取未转化过的出库单
     *
     * @return
     */
    List<JstOrderOutDTO> getNotTranOrderOut();

    /**
     * 获取客商代发单的发货单信息
     *
     * @return
     */
    List<JstOrderOutDTO> getImportOrder();

}