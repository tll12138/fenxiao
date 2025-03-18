package cn.iocoder.yudao.module.fx.service.monsettlement;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.fx.controller.admin.monsettlement.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.monsettlement.MonSettlementDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 分销账户月结 Service 接口
 *
 * @author 管理员
 */
public interface MonSettlementService {

    /**
     * 创建分销账户月结
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createMonSettlement(@Valid MonSettlementSaveReqVO createReqVO);

    /**
     * 更新分销账户月结
     *
     * @param updateReqVO 更新信息
     */
    void updateMonSettlement(@Valid MonSettlementSaveReqVO updateReqVO);

    /**
     * 删除分销账户月结
     *
     * @param id 编号
     */
    void deleteMonSettlement(Integer id);

    /**
     * 获得分销账户月结
     *
     * @param id 编号
     * @return 分销账户月结
     */
    MonSettlementDO getMonSettlement(Integer id);

    /**
     * 获得分销账户月结分页
     *
     * @param pageReqVO 分页查询
     * @return 分销账户月结分页
     */
    PageResult<MonSettlementDO> getMonSettlementPage(MonSettlementPageReqVO pageReqVO);

}