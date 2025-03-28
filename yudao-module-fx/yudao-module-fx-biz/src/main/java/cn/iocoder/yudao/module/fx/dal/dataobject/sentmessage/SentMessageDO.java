package cn.iocoder.yudao.module.fx.dal.dataobject.sentmessage;

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

import java.time.LocalDateTime;

/**
 * 分销发货要求消息 DO
 *
 * @author 管理员
 */
@TableName("fx_sent_message")
@KeySequence("fx_sent_message_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SentMessageDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Long id;
    /**
     * 类型
     */
    private String type;
    /**
     * 单据编号
     */
    private String soId;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 计划发送时间
     */
    private LocalDateTime sendTime;
    /**
     * webhook
     */
    private String webhook;
    /**
     * secret
     */
    private String secret;
    /**
     * 仓库
     */
    private Integer warehouseId;
    /**
     * 是否发送
     * <p>
     * 枚举 {@link TODO yes_no 对应的类}
     */
    private Integer isSend;

}