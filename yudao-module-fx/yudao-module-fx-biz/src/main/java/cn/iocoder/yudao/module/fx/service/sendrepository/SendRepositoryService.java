package cn.iocoder.yudao.module.fx.service.sendrepository;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo.SendRepositoryPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo.SendRepositorySaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository.SendRepositoryDO;

import javax.validation.Valid;
import java.util.List;

/**
 * FX 发货仓库 Service 接口
 *
 * @author 管理员
 */
public interface SendRepositoryService {

    /**
     * 创建FX 发货仓库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createSendRepository(@Valid SendRepositorySaveReqVO createReqVO);

    /**
     * 更新FX 发货仓库
     *
     * @param updateReqVO 更新信息
     */
    void updateSendRepository(@Valid SendRepositorySaveReqVO updateReqVO);

    /**
     * 删除FX 发货仓库
     *
     * @param id 编号
     */
    void deleteSendRepository(Integer id);

    /**
     * 获得FX 发货仓库
     *
     * @param id 编号
     * @return FX 发货仓库
     */
    SendRepositoryDO getSendRepository(Integer id);

    /**
     * 获得FX 发货仓库分页
     *
     * @param pageReqVO 分页查询
     * @return FX 发货仓库分页
     */
    PageResult<SendRepositoryDO> getSendRepositoryPage(SendRepositoryPageReqVO pageReqVO);

    /**
     * 同步发货仓库信息
     */
    void syncSendRepository();

    /**
     * 获得全量可用非内部发货仓库
     *
     * @return 发货仓库
     */
    List<SendRepositoryDO> getSendRepositoryList();
}