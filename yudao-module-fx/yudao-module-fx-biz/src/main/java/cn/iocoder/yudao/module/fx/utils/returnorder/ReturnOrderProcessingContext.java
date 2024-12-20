package cn.iocoder.yudao.module.fx.utils.returnorder;

import cn.iocoder.yudao.module.fx.controller.admin.returnorder.vo.ReturnOrderSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.returnorder.ReturnOrderDO;
import cn.iocoder.yudao.module.fx.utils.template.BaseProcessingContext;
import lombok.Builder;
import lombok.Data;

/**
 * @author zrl
 * @date 2024/12/6
 */
@Data
@Builder
public class ReturnOrderProcessingContext extends BaseProcessingContext {

    /**
     * 退货单创建信息
     */
    ReturnOrderSaveReqVO returnOrderSaveReqVO;


    /**
     * 退货单信息
     */
    ReturnOrderDO returnOrderDO;

}
