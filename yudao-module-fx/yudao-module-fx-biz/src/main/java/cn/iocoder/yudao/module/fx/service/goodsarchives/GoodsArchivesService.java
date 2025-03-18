package cn.iocoder.yudao.module.fx.service.goodsarchives;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.goodsarchives.vo.GoodsArchivesPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.goodsarchives.vo.GoodsArchivesSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.goodsarchives.GoodsArchivesDO;

import javax.validation.Valid;

/**
 * 分销商品资料 Service 接口
 *
 * @author 管理员
 */
public interface GoodsArchivesService {

    /**
     * 创建分销商品资料
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createGoodsArchives(@Valid GoodsArchivesSaveReqVO createReqVO);

    /**
     * 更新分销商品资料
     *
     * @param updateReqVO 更新信息
     */
    void updateGoodsArchives(@Valid GoodsArchivesSaveReqVO updateReqVO);

    /**
     * 删除分销商品资料
     *
     * @param id 编号
     */
    void deleteGoodsArchives(Integer id);

    /**
     * 获得分销商品资料
     *
     * @param id 编号
     * @return 分销商品资料
     */
    GoodsArchivesDO getGoodsArchives(Integer id);

    /**
     * 获得分销商品资料分页
     *
     * @param pageReqVO 分页查询
     * @return 分销商品资料分页
     */
    PageResult<GoodsArchivesDO> getGoodsArchivesPage(GoodsArchivesPageReqVO pageReqVO);

    /**
     * 根据仓库编码获得分销商品资料分页
     *
     * @param pageReqVO 分页查询
     * @return 分销商品资料分页
     */
    PageResult<GoodsArchivesDO> getGoodsArchivesPageByWarehouseCode(GoodsArchivesPageReqVO pageReqVO);

    /**
     * 同步分销商品信息
     */
    void syncGoodsArchives(boolean ifAll);
}