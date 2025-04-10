package cn.iocoder.yudao.module.fx.service.jstaftersale;

import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersale.JstAfterSaleDO;
import cn.iocoder.yudao.module.fx.dal.mysql.jstaftersale.JstAfterSaleMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 分销退货传聚水潭中间 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class JstAfterSaleServiceImpl implements JstAfterSaleService {

    @Resource
    private JstAfterSaleMapper jstAfterSaleMapper;

    @Override
    public Long createJstAfterSale(JstAfterSaleDO jstAfterSale) {
        // 插入
        jstAfterSaleMapper.insert(jstAfterSale);
        // 返回
        return jstAfterSale.getId();
    }

    @Override
    public JstAfterSaleDO getJstAfterSale(Long id) {
        return jstAfterSaleMapper.selectById(id);
    }

}