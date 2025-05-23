package cn.iocoder.yudao.module.fx.dal.dataobject.bigcustomeraddress;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 分销大客户地址 DO
 *
 * @author 管理员
 */
@TableName("fx_big_customer_address")
@KeySequence("fx_big_customer_address_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BigCustomerAddressDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Long id;
    /**
     * 分销商
     */
    private Integer customerId;
    /**
     * 分销商名称
     */
    private String customerName;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 地址
     */
    private String address;
    /**
     * 联系人
     */
    private String person;
    /**
     * 联系电话
     */
    private String contact;
    /**
     * 是否可用
     * <p>
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private String isActive;
    /**
     * 审批状态
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 所属品牌
     * <p>
     * 枚举 {@link TODO fx_brand 对应的类}
     */
    private String brand;
    /**
     * 使用次数
     */
    private BigDecimal count;

}