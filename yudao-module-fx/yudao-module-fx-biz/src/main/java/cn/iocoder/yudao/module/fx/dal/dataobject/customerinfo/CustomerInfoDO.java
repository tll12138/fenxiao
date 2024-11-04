package cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 分销商基础信息 DO
 *
 * @author 管理员
 */
@TableName("fx_customer_info")
@KeySequence("fx_customer_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 供应商编号
     */
    private Long supplierId;
    /**
     * 分销商编号
     */
    private Long distributorNum;
    /**
     * 分销商名称
     */
    private String distributorName;
    /**
     * 所属子公司
     */
    private Long subCompany;
    /**
     * 显示名称
     */
    private String displayName;
    /**
     * 业务归属
     *
     * 枚举 {@link TODO fx_belong 对应的类}
     */
    private String belongTo;
    /**
     * 分销商等级
     *
     * 枚举 {@link TODO fx_customer_level 对应的类}
     */
    private Integer distributorLevel;
    /**
     * 是否合作
     *
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private Integer isCooperate;
    /**
     * 是否冻结
     *
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private Integer isFreeze;
    /**
     * 客户渠道属性
     *
     * 枚举 {@link TODO fx_customer_distribute 对应的类}
     */
    private String customerChannelDistribute;
    /**
     * 品牌
     *
     * 枚举 {@link TODO fx_brand 对应的类}
     */
    private String brand;
    /**
     * 客户类型
     *
     * 枚举 {@link TODO fx_customer_type 对应的类}
     */
    private Integer customerType;
    /**
     * 最近下单时间
     */
    private LocalDateTime latestOrderDate;
    /**
     * 是否允许超额提货
     *
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private Integer isExcessOrder;

}