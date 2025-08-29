package cn.iocoder.yudao.module.fx.dal.dataobject.brandauth;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 品牌授权 DO
 *
 * @author 管理员
 */
@TableName("fx_brand_auth")
@KeySequence("fx_brand_auth_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandAuthDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * ip
     */
    private String ip;
    /**
     * 唯一标识
     */
    private String uuid;
    /**
     * 设备
     */
    private String clientDevice;
    /**
     * 操作系统
     */
    private String clientOs;
    /**
     * 浏览器
     */
    private String clientBrowser;
    /**
     * 备注
     */
    private String remark;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 公司名称/旺旺id
     */
    private String storeId;
    /**
     * 店铺链接
     */
    private String storeUrl;
    /**
     * 授权书接收邮箱
     */
    private String email;
    /**
     * 申请日期
     */
    private String applyDate;
    /**
     * 申请时间
     */
    private String applyDatetime;
    /**
     * 是否已阅授权书
     */
    private String isSee;
    /**
     * 店铺渠道
     */
    private BigDecimal channel;
    /**
     * 关联流程
     */
    private BigDecimal workflowid;
    /**
     * 是否线上
     */
    private String isOnline;
    /**
     * 品牌
     */
    private String brand;

}