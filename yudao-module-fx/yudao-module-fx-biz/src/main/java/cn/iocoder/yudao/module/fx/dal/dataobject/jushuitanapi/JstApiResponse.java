package cn.iocoder.yudao.module.fx.dal.dataobject.jushuitanapi;

import lombok.Builder;
import lombok.Data;

/**
 * @author tll
 * @date 2025-04-16 14:08:02
 */
@Data
@Builder
public class JstApiResponse {
    private Integer code;
    private String msg;
}
