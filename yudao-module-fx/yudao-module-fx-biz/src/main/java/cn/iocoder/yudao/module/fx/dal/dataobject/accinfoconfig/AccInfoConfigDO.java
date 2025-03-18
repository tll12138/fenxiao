package cn.iocoder.yudao.module.fx.dal.dataobject.accinfoconfig;

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

/**
 * 客商账户初始化配置 DO
 *
 * @author 管理员
 */
@TableName("fx_acc_info_config")
@KeySequence("fx_acc_info_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccInfoConfigDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Integer id;
    /**
     * 业务主体
     */
    private Integer ywzt;
    /**
     * 说明
     */
    private String about;
    /**
     * 关联品牌
     */
    private Integer brandid;

}