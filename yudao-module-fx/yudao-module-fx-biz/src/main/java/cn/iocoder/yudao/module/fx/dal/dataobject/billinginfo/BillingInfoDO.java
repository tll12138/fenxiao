package cn.iocoder.yudao.module.fx.dal.dataobject.billinginfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 开票信息 DO
 *
 * @author 管理员
 */
@TableName("fx_billing_info")
@KeySequence("fx_billing_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingInfoDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 客商
     */
    private Integer customerId;
    /**
     * 购方名称
     */
    private String company;
    /**
     * 纳税人识别号
     */
    private String tax;
    /**
     * 开户行及账号
     */
    private String bank;
    /**
     * 地址及电话
     */
    private String address;
    /**
     * 是否生效
     *
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private String isActive;
    /**
     * 备注
     */
    private String remark;
    /**
     * 发送邮箱
     */
    private String email;

}