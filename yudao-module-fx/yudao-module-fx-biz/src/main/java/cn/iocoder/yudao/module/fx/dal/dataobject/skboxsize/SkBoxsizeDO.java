package cn.iocoder.yudao.module.fx.dal.dataobject.skboxsize;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 商品箱规 DO
 *
 * @author 管理员
 */
@TableName("fx_sk_boxsize")
@KeySequence("fx_sk_boxsize_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkBoxsizeDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Long id;
    /**
     * 商品编码
     */
    private String skuId;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 箱规
     */
    private BigDecimal boxSize;

}