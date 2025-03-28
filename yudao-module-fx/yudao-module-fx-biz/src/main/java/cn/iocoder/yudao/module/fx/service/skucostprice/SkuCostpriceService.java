package cn.iocoder.yudao.module.fx.service.skucostprice;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.fx.controller.admin.skucostprice.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.skucostprice.SkuCostpriceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 商品成本 Service 接口
 *
 * @author 管理员
 */
public interface SkuCostpriceService {

    /**
     * 创建商品成本
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSkuCostprice(@Valid SkuCostpriceSaveReqVO createReqVO);

    /**
     * 更新商品成本
     *
     * @param updateReqVO 更新信息
     */
    void updateSkuCostprice(@Valid SkuCostpriceSaveReqVO updateReqVO);

    /**
     * 删除商品成本
     *
     * @param id 编号
     */
    void deleteSkuCostprice(Long id);

    /**
     * 获得商品成本
     *
     * @param id 编号
     * @return 商品成本
     */
    SkuCostpriceDO getSkuCostprice(Long id);

    /**
     * 获得商品成本分页
     *
     * @param pageReqVO 分页查询
     * @return 商品成本分页
     */
    PageResult<SkuCostpriceDO> getSkuCostpricePage(SkuCostpricePageReqVO pageReqVO);

}