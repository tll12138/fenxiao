package cn.iocoder.yudao.module.fx.service.pricelist;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.pricelist.vo.PricelistSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.pricelist.PricelistDO;

import javax.validation.Valid;

/**
 * 分销价格对照 Service 接口
 *
 * @author 管理员
 */
public interface PricelistService {

    /**
     * 创建分销价格对照
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createPricelist(@Valid PricelistSaveReqVO createReqVO);

    /**
     * 更新分销价格对照
     *
     * @param updateReqVO 更新信息
     */
    void updatePricelist(@Valid PricelistSaveReqVO updateReqVO);

    /**
     * 删除分销价格对照
     *
     * @param id 编号
     */
    void deletePricelist(Integer id);

    /**
     * 获得分销价格对照
     *
     * @param id 编号
     * @return 分销价格对照
     */
    PricelistDO getPricelist(Integer id);

    /**
     * 获得分销价格对照分页
     *
     * @param pageReqVO 分页查询
     * @return 分销价格对照分页
     */
    PageResult<PricelistDO> getPricelistPage(PricelistPageReqVO pageReqVO);

    /**
     * 分销商层级调整后触发，将价格对照表同SKU直接按基础更新
     *
     * @param id
     */
    void processPriceUpdate(Integer id);
}