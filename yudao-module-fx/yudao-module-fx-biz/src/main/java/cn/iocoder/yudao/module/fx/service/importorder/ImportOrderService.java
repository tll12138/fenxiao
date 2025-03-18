package cn.iocoder.yudao.module.fx.service.importorder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderExcelRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderExcelVO;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.importorder.vo.ImportOrderSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.importorder.ImportOrderDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 客商代发单 Service 接口
 *
 * @author 管理员
 */
public interface ImportOrderService {

    /**
     * 创建客商代发单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createImportOrder(@Valid ImportOrderSaveReqVO createReqVO);

    /**
     * 更新客商代发单
     *
     * @param updateReqVO 更新信息
     */
    void updateImportOrder(@Valid ImportOrderSaveReqVO updateReqVO);

    /**
     * 删除客商代发单
     *
     * @param id 编号
     */
    void deleteImportOrder(Integer id);

    /**
     * 获得客商代发单
     *
     * @param id 编号
     * @return 客商代发单
     */
    ImportOrderDO getImportOrder(Integer id);

    /**
     * 获得客商代发单分页
     *
     * @param pageReqVO 分页查询
     * @return 客商代发单分页
     */
    PageResult<ImportOrderDO> getImportOrderPage(ImportOrderPageReqVO pageReqVO);

    /**
     * 导入客商代发单数据
     *
     * @param list          导入数据列表，包含需要导入的客商代发单信息
     * @param updateSupport 是否支持更新已存在数据（true：更新；false：跳过）
     * @return 导入结果响应，包含成功与失败数量
     */
    ImportOrderExcelRespVO importOrderList(List<ImportOrderExcelVO> list, Boolean updateSupport);
}