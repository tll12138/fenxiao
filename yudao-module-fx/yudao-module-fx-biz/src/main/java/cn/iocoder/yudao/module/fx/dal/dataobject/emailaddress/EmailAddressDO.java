package cn.iocoder.yudao.module.fx.dal.dataobject.emailaddress;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 发票邮箱库 DO
 *
 * @author 管理员
 */
@TableName("fx_email_address")
@KeySequence("fx_email_address_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailAddressDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 用户编号
     */
    private String customerId;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 备注
     */
    private String remark;
    /**
     * 开票抬头
     */
    private String company;
    /**
     * 税号
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
     * 是否可用
     *
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private String isActive;

}