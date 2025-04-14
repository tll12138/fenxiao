package cn.iocoder.yudao.module.fx.service.jstaftersaledata;

import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersaledata.JstAfterSaleDataDO;

import java.util.List;

/**
 * 分销退货传聚水潭明细中间 Service 接口
 *
 * @author 管理员
 */
public interface JstAfterSaleDataService {

    /**
     * 创建分销退货传聚水潭明细中间
     *
     * @return 编号
     */
    Long createJstAfterSaleData(JstAfterSaleDataDO jstAfterSaleData);

    /**
     * 获得分销退货传聚水潭明细中间
     *
     * @param id 编号
     * @return 分销退货传聚水潭明细中间
     */
    JstAfterSaleDataDO getJstAfterSaleData(Long id);

    /**
     * 根据主表id获取明细数据
     *
     * @param mainId
     * @return
     */
    List<JstAfterSaleDataDO> getJstAfterSaleDataListByMainId(Long mainId);

    void saveBatch(List<JstAfterSaleDataDO> jstAfterSaleDataDOList);

}