package cn.iocoder.yudao.module.fx.service.jushuitanapi;

import com.jushuitan.api.ApiResponse;

/**
 * @author tll
 * @date 2025-02-21 17:19:03
 */
public interface JuShuiTanApiService {

    /**
     * 调用聚水潭接口
     *
     * @param urlKey 获取api接口的key
     * @param biz    组成request的参数
     * @return response
     */
    ApiResponse execute(String urlKey, String biz);
}
