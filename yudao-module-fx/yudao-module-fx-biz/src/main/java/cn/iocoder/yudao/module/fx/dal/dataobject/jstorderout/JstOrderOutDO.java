package cn.iocoder.yudao.module.fx.dal.dataobject.jstorderout;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 聚水潭发货回传中间表 DO
 *
 * @author 管理员
 */
@TableName("fx_jst_order_out")
@KeySequence("fx_jst_order_out_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JstOrderOutDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Long id;
    /**
     * 店铺编号
     */
    private Long shopId;
    /**
     * 内部单号
     */
    private Integer oId;
    /**
     * 销售单号
     */
    private String soId;
    /**
     * 快递公司
     */
    private String expressName;
    /**
     * 快递单号
     */
    private String express;
    /**
     * 快递编码
     */
    private String expressCode;
    /**
     * 转换标记
     *
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private String isTran;
    /**
     * 修改时间
     */
    private LocalDateTime modified;

}