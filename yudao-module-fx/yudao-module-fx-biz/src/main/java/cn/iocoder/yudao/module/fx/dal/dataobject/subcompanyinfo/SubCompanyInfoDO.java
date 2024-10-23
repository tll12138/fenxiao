package cn.iocoder.yudao.module.fx.dal.dataobject.subcompanyinfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import lombok.experimental.FieldNameConstants;

/**
 * 子公司信息 DO
 *
 * @author 管理员
 */
@TableName("fx_sub_company_info")
@KeySequence("fx_sub_company_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class SubCompanyInfoDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 识别人编号
     */
    private String identifyId;
    /**
     * 开户行
     */
    private String bank;
    /**
     * 地区
     */
    private String region;
    /**
     * 是否是初始化公司账户
     */
    private Integer isInitCompany;

}