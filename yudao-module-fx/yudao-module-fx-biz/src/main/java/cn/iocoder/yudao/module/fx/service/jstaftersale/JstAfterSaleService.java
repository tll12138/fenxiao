package cn.iocoder.yudao.module.fx.service.jstaftersale;

import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale.JstAfterSaleDO;

/**
 * 分销退货传聚水潭中间 Service 接口
 *
 * @author 管理员
 */
public interface JstAfterSaleService {

    /**
     * 创建分销退货传聚水潭中间
     *
     * @return 编号
     */
    Long createJstAfterSale(JstAfterSaleDO jstAfterSale);

    /**
     * 获得分销退货传聚水潭中间
     *
     * @param id 编号
     * @return 分销退货传聚水潭中间
     */
    JstAfterSaleDO getJstAfterSale(Long id);

    /**
     * 调用聚水潭售后api
     */
    void updateCallERP();
}