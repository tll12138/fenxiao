package cn.iocoder.yudao.module.fx.service.accinfoconfig;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.accinfoconfig.vo.AccInfoConfigPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.accinfoconfig.vo.AccInfoConfigSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.accinfoconfig.AccInfoConfigDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 客商账户初始化配置 Service 接口
 *
 * @author 管理员
 */
public interface AccInfoConfigService {

    /**
     * 创建客商账户初始化配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createAccInfoConfig(@Valid AccInfoConfigSaveReqVO createReqVO);

    /**
     * 更新客商账户初始化配置
     *
     * @param updateReqVO 更新信息
     */
    void updateAccInfoConfig(@Valid AccInfoConfigSaveReqVO updateReqVO);

    /**
     * 删除客商账户初始化配置
     *
     * @param id 编号
     */
    void deleteAccInfoConfig(Integer id);

    /**
     * 获得客商账户初始化配置
     *
     * @param id 编号
     * @return 客商账户初始化配置
     */
    AccInfoConfigDO getAccInfoConfig(Integer id);

    /**
     * 获得客商账户初始化配置分页
     *
     * @param pageReqVO 分页查询
     * @return 客商账户初始化配置分页
     */
    PageResult<AccInfoConfigDO> getAccInfoConfigPage(AccInfoConfigPageReqVO pageReqVO);

    /**
     * 获得所有客商账户初始化配置
     *
     * @return
     */
    List<AccInfoConfigDO> getAllAccInfoConfig();
}