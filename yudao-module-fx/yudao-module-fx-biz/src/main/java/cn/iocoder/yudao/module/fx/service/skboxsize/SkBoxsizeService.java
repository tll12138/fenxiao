package cn.iocoder.yudao.module.fx.service.skboxsize;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.fx.controller.admin.skboxsize.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.skboxsize.SkBoxsizeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 商品箱规 Service 接口
 *
 * @author 管理员
 */
public interface SkBoxsizeService {

    /**
     * 创建商品箱规
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSkBoxsize(@Valid SkBoxsizeSaveReqVO createReqVO);

    /**
     * 更新商品箱规
     *
     * @param updateReqVO 更新信息
     */
    void updateSkBoxsize(@Valid SkBoxsizeSaveReqVO updateReqVO);

    /**
     * 删除商品箱规
     *
     * @param id 编号
     */
    void deleteSkBoxsize(Long id);

    /**
     * 获得商品箱规
     *
     * @param id 编号
     * @return 商品箱规
     */
    SkBoxsizeDO getSkBoxsize(Long id);

    /**
     * 获得商品箱规分页
     *
     * @param pageReqVO 分页查询
     * @return 商品箱规分页
     */
    PageResult<SkBoxsizeDO> getSkBoxsizePage(SkBoxsizePageReqVO pageReqVO);

}