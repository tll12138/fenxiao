package cn.iocoder.yudao.module.fx.dal.dataobject.manualdelivery;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 手动发货信息 DO
 *
 * @author 管理员
 */
@TableName("fx_manual_delivery")
@KeySequence("fx_manual_delivery_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManualDeliveryDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Long id;
    /**
     * 快递公司
     *
     * 枚举 {@link TODO fx_wl 对应的类}
     */
    private String expressCompany;
    /**
     * 快递公司名称
     */
    private String expressName;
    /**
     * 快递公司编号
     */
    private String expressId;
    /**
     * 快递单号
     */
    private String express;
    /**
     * 销售单号
     */
    private String soId;
    /**
     * 关联销售单
     */
    private String saleId;
    /**
     * 单据状态
     */
    private Integer status;
    /**
     * 手动发货原因
     */
    private String reason;

}