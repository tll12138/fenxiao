package cn.iocoder.yudao.module.fx.dal.dataobject.inventorydata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author tll
 * @date 2025-01-14 14:55:44
 */
@Data
public class InventoryVO {
    /**
     * 商品编码
     */
    @JSONField(name = "sku_id")
    private String skuId;
    /**
     * 时间戳
     */
    private long ts;
    /**
     * 款式编码
     */
    @JSONField(name = "i_id")
    private String iId;
    /**
     * 主仓实际库存
     */
    @JSONField(name = "qty")
    private int qty;
    /**
     * 订单占有数
     */
    @JSONField(name = "order_lock")
    private int orderLock;
    /**
     * 仓库待发数
     */
    @JSONField(name = "pick_lock")
    private int pickLock;
    /**
     * 虚拟库存
     */
    @JSONField(name = "virtual_qty")
    private int virtualQty;
    /**
     * 采购在途数
     */
    @JSONField(name = "purchase_qty")
    private int purchaseQty;
    /**
     * 销退仓库存
     */
    @JSONField(name = "return_qty")
    private int returnQty;
    /**
     * 进货仓库存
     */
    @JSONField(name = "in_qty")
    private int inQty;
    /**
     * 次品库存
     */
    @JSONField(name = "defective_qty")
    private int defectiveQty;
    /**
     * 修改时间
     */
    @JSONField(name = "modified")
    private String modified;
    /**
     * 安全库存下限
     */
    @JSONField(name = "min_qty")
    private int minQty;
    /**
     * 安全库存上限
     */
    @JSONField(name = "max_qty")
    private int maxQty;
    /**
     * 库存锁定数
     */
    @JSONField(name = "lock_qty")
    private int lockQty;
    /**
     * 商品名称
     */
    @JSONField(name = "name")
    private String name;
    /**
     * 自定义仓1
     */
    @JSONField(name = "customize_qty_1")
    private int customizeQty1;
    /**
     * 自定义仓2
     */
    @JSONField(name = "customize_qty_2")
    private int customizeQty2;
    /**
     * 自定义仓3
     */
    @JSONField(name = "customize_qty_3")
    private int customizeQty3;
    /**
     * 调拨在途数
     */
    @JSONField(name = "allocate_qty")
    private int allocateQty;
    /**
     * 销退在途数
     */
    @JSONField(name = "sale_refund_qty")
    private int saleRefundQty;
}
