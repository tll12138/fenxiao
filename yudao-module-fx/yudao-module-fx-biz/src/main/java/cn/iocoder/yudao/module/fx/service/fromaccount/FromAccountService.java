package cn.iocoder.yudao.module.fx.service.fromaccount;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.fx.controller.admin.fromaccount.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.fromaccount.FromAccountDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 *  分销打款账户 Service 接口
 *
 * @author 管理员
 */
public interface FromAccountService {

    /**
     * 创建 分销打款账户
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createFromAccount(@Valid FromAccountSaveReqVO createReqVO);

    /**
     * 更新 分销打款账户
     *
     * @param updateReqVO 更新信息
     */
    void updateFromAccount(@Valid FromAccountSaveReqVO updateReqVO);

    /**
     * 删除 分销打款账户
     *
     * @param id 编号
     */
    void deleteFromAccount(Integer id);

    /**
     * 获得 分销打款账户
     *
     * @param id 编号
     * @return  分销打款账户
     */
    FromAccountDO getFromAccount(Integer id);

    /**
     * 获得 分销打款账户分页
     *
     * @param pageReqVO 分页查询
     * @return  分销打款账户分页
     */
    PageResult<FromAccountDO> getFromAccountPage(FromAccountPageReqVO pageReqVO);

}