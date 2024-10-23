package cn.iocoder.yudao.module.fx.service.subcompanyinfo;

import java.util.*;
import cn.iocoder.yudao.module.fx.controller.admin.subcompanyinfo.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.subcompanyinfo.SubCompanyInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.diboot.core.service.BaseService;

import javax.validation.Valid;

/**
 * 子公司信息 Service 接口
 *
 * @author 管理员
 */
public interface SubCompanyInfoService extends BaseService<SubCompanyInfoDO> {

    /**
     * 创建子公司信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSubCompanyInfo(@Valid SubCompanyInfoSaveReqVO createReqVO);

    /**
     * 更新子公司信息
     *
     * @param updateReqVO 更新信息
     */
    void updateSubCompanyInfo(@Valid SubCompanyInfoSaveReqVO updateReqVO);

    /**
     * 删除子公司信息
     *
     * @param id 编号
     */
    void deleteSubCompanyInfo(Long id);

    /**
     * 获得子公司信息
     *
     * @param id 编号
     * @return 子公司信息
     */
    SubCompanyInfoDO getSubCompanyInfo(Long id);

    /**
     * 获得子公司信息分页
     *
     * @param pageReqVO 分页查询
     * @return 子公司信息分页
     */
    PageResult<SubCompanyInfoDO> getSubCompanyInfoPage(SubCompanyInfoPageReqVO pageReqVO);

    List<SubCompanyInfoRespVO> getSubCompanyInfoList();
}