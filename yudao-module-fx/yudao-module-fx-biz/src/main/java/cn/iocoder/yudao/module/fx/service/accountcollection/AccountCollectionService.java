package cn.iocoder.yudao.module.fx.service.accountcollection;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.fx.controller.admin.accountcollection.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.accountcollection.AccountCollectionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 分销账户收款记录 Service 接口
 *
 * @author 管理员
 */
public interface AccountCollectionService {

    /**
     * 创建分销账户收款记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createAccountCollection(@Valid AccountCollectionSaveReqVO createReqVO);

    /**
     * 更新分销账户收款记录
     *
     * @param updateReqVO 更新信息
     */
    void updateAccountCollection(@Valid AccountCollectionSaveReqVO updateReqVO);

    /**
     * 删除分销账户收款记录
     *
     * @param id 编号
     */
    void deleteAccountCollection(Integer id);

    /**
     * 获得分销账户收款记录
     *
     * @param id 编号
     * @return 分销账户收款记录
     */
    AccountCollectionDO getAccountCollection(Integer id);

    /**
     * 获得分销账户收款记录分页
     *
     * @param pageReqVO 分页查询
     * @return 分销账户收款记录分页
     */
    PageResult<AccountCollectionDO> getAccountCollectionPage(AccountCollectionPageReqVO pageReqVO);

}