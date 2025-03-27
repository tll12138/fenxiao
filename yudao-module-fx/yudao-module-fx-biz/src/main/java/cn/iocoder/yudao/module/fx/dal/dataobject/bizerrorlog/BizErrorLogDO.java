package cn.iocoder.yudao.module.fx.dal.dataobject.bizerrorlog;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 业务错误日志 DO
 *
 * @author 管理员
 */
@TableName("fx_biz_error_log")
@KeySequence("fx_biz_error_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizErrorLogDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 模块名
     */
    private String module;
    /**
     * 错误类型（类名）
     */
    private String type;
    /**
     * 业务ID
     */
    private String bizId;
    /**
     * 错误数据（JSON格式）
     */
    private String errorData;
    /**
     * 错误码
     */
    private Integer errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 请求追踪ID
     */
    private String traceId;
    /**
     * 请求IP
     */
    private String requestIp;
    /**
     * 请求参数
     */
    private String requestParams;

}