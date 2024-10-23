package cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 分销商地址 DO
 *
 * @author 管理员
 */
@TableName("fx_customer_address")
@KeySequence("fx_customer_address_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 分销商编号
     */
    private Long distributorId;
    /**
     * 联系人
     */
    private String manager;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 省份
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
     * 发货地址
     */
    private String address;

}