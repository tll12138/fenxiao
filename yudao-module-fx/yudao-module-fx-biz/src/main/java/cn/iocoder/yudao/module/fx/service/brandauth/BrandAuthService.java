package cn.iocoder.yudao.module.fx.service.brandauth;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.fx.controller.admin.brandauth.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.brandauth.BrandAuthDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 品牌授权 Service 接口
 *
 * @author 管理员
 */
public interface BrandAuthService {

    /**
     * 创建品牌授权
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createBrandAuth(@Valid BrandAuthSaveReqVO createReqVO);

    /**
     * 更新品牌授权
     *
     * @param updateReqVO 更新信息
     */
    void updateBrandAuth(@Valid BrandAuthSaveReqVO updateReqVO);

    /**
     * 删除品牌授权
     *
     * @param id 编号
     */
    void deleteBrandAuth(Integer id);

    /**
     * 获得品牌授权
     *
     * @param id 编号
     * @return 品牌授权
     */
    BrandAuthDO getBrandAuth(Integer id);

    /**
     * 获得品牌授权分页
     *
     * @param pageReqVO 分页查询
     * @return 品牌授权分页
     */
    PageResult<BrandAuthDO> getBrandAuthPage(BrandAuthPageReqVO pageReqVO);

}