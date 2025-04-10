package cn.iocoder.yudao.module.fx.service.jstaftersaledata;

import cn.iocoder.yudao.module.fx.dal.dataobject.jstaftersaledata.JstAfterSaleDataDO;
import cn.iocoder.yudao.module.fx.dal.mysql.jstaftersaledata.JstAfterSaleDataMapper;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分销退货传聚水潭明细中间 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class JstAfterSaleDataServiceImpl implements JstAfterSaleDataService {

    @Resource
    private JstAfterSaleDataMapper jstAfterSaleDataMapper;

    @Override
    public Long createJstAfterSaleData(JstAfterSaleDataDO jstAfterSaleData) {
        // 插入
        jstAfterSaleDataMapper.insert(jstAfterSaleData);
        // 返回
        return jstAfterSaleData.getId();
    }

    @Override
    public JstAfterSaleDataDO getJstAfterSaleData(Long id) {
        return jstAfterSaleDataMapper.selectById(id);
    }

    @Override
    public void saveBatch(List<JstAfterSaleDataDO> jstAfterSaleDataDOList) {
        if (CollectionUtil.isEmpty(jstAfterSaleDataDOList)) {
            return;
        }
        jstAfterSaleDataMapper.insertBatch(jstAfterSaleDataDOList);
    }

}