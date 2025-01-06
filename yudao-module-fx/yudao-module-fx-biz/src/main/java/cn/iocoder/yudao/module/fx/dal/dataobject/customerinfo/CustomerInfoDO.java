package cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.util.Date;

/**
 * 分销商基础信息 DO
 *
 * @author 管理员
 */
@TableName("fx_customer_info")
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
    private String supplierId;
    /**
     * 分销商编号
     */
    private String distributorNum;
    /**
     * 分销商名称
     */
    private String distributorName;
    /**
     * 所属子公司
     */
    private String subCompany;
    /**
     * 显示名称
     */
    private String displayName;
    /**
     * 业务归属
     * <p>
     * 枚举 {@link TODO fx_belong 对应的类}
     */
    private String belongTo;
    /**
     * 分销商等级
     * <p>
     * 枚举 {@link TODO fx_customer_level 对应的类}
     */
    private Integer distributorLevel;
    /**
     * 是否合作
     * <p>
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private Integer isCooperate;
    /**
     * 是否冻结
     * <p>
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private Integer isFreeze;
    /**
     * 客户渠道属性
     * <p>
     * 枚举 {@link TODO fx_customer_distribute 对应的类}
     */
    private String customerChannelDistribute;
    /**
     * 品牌
     * <p>
     * 枚举 {@link TODO fx_brand 对应的类}
     */
    private String brand;
    /**
     * 客户类型
     * <p>
     * 枚举 {@link TODO fx_customer_type 对应的类}
     */
    private Integer customerType;
    /**
     * 最近下单时间
     */
    private Date latestOrderDate;
    /**
     * 是否允许超额提货
     * <p>
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private Integer isExcessOrder;
    /**
     * 备注
     */
    private String remark;
    /**
     * 业务员
     */
    private String salesman;
    /**
     * 状态
     */
    private String status;
    /**
     * 发票抬头
     */
    private String company;
    /**
     * 税号
     */
    private String tax;
    /**
     * 开户行
     */
    private String bank;
    /**
     * 开户地址
     */
    private String address;
    /**
     * 银行卡号
     */
    private String bankNo;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 物流
     */
    private String express;
    /**
     * 简称
     */
    private String nickName;
    /**
     * 是否关系户
     * <p>
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private String isBestFriend;
    /**
     * 是否毛保客户
     * <p>
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private String isMcus;
    /**
     * 密码
     */
    private String password;
    /**
     * 货补规则
     */
    private String repRule;
    /**
     * 修改时间
     */
    private Date modified;
    /**
     * 申请时间
     */
    private Date applyTime;
    /**
     * 合作时间
     */
    private Date confirmTime;


}