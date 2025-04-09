package cn.iocoder.yudao.module.fx.service.returnorderdetail;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.fx.controller.admin.returnorderdetail.vo.*;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorderdetail.ReturnOrderDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 销售退货详情 Service 接口
 *
 * @author 管理员
 */
public interface ReturnOrderDetailService {

    /**
     * 创建销售退货详情
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReturnOrderDetail(@Valid ReturnOrderDetailSaveReqVO createReqVO);

    /**
     * 更新销售退货详情
     *
     * @param updateReqVO 更新信息
     */
    void updateReturnOrderDetail(@Valid ReturnOrderDetailSaveReqVO updateReqVO);

    /**
     * 删除销售退货详情
     *
     * @param id 编号
     */
    void deleteReturnOrderDetail(Long id);

    /**
     * 获得销售退货详情
     *
     * @param id 编号
     * @return 销售退货详情
     */
    ReturnOrderDetailDO getReturnOrderDetail(Long id);

    /**
     * 获得销售退货详情分页
     *
     * @param pageReqVO 分页查询
     * @return 销售退货详情分页
     */
    PageResult<ReturnOrderDetailDO> getReturnOrderDetailPage(ReturnOrderDetailPageReqVO pageReqVO);

}