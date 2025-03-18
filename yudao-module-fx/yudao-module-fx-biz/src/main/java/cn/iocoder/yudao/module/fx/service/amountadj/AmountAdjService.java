package cn.iocoder.yudao.module.fx.service.amountadj;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.fx.controller.admin.amountadj.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.amountadj.AmountAdjDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 分销账户资金调整记录 Service 接口
 *
 * @author 管理员
 */
public interface AmountAdjService {

    /**
     * 创建分销账户资金调整记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createAmountAdj(@Valid AmountAdjSaveReqVO createReqVO);

    /**
     * 更新分销账户资金调整记录
     *
     * @param updateReqVO 更新信息
     */
    void updateAmountAdj(@Valid AmountAdjSaveReqVO updateReqVO);

    /**
     * 删除分销账户资金调整记录
     *
     * @param id 编号
     */
    void deleteAmountAdj(Integer id);

    /**
     * 获得分销账户资金调整记录
     *
     * @param id 编号
     * @return 分销账户资金调整记录
     */
    AmountAdjDO getAmountAdj(Integer id);

    /**
     * 获得分销账户资金调整记录分页
     *
     * @param pageReqVO 分页查询
     * @return 分销账户资金调整记录分页
     */
    PageResult<AmountAdjDO> getAmountAdjPage(AmountAdjPageReqVO pageReqVO);

}