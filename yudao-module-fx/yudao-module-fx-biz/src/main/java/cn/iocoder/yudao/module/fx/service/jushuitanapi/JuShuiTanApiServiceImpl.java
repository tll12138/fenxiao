package cn.iocoder.yudao.module.fx.service.jushuitanapi;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.module.fx.service.bizerrorlog.BizErrorLogService;
import cn.iocoder.yudao.module.fx.utils.MapUtils;
import cn.iocoder.yudao.module.system.service.dict.DictDataService;
import com.jushuitan.api.ApiClient;
import com.jushuitan.api.ApiRequest;
import com.jushuitan.api.ApiResponse;
import com.jushuitan.api.DefaultApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DICT_TYPE_NOT_EXISTS;

/**
 * @author tll
 * @date 2025-02-21 17:22:19
 */
@Slf4j
@Service
@Validated
public class JuShuiTanApiServiceImpl implements JuShuiTanApiService {
    @Resource
    private DictDataService dictDataService;
    @Resource
    private BizErrorLogService bizErrorLogService;

    @Override
    public ApiResponse execute(String urlKey, String biz) {
        //从数据字典获取接口数据
        Map<String, String> apiInfo = dictDataService.getDictDataMapByDictType("fx_jushuitan_API_info");
        if (MapUtils.isEmpty(apiInfo)) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        String url = this.getRequiredConfig(apiInfo, urlKey);
        String appKey = this.getRequiredConfig(apiInfo, "appKey");
        String appSecret = this.getRequiredConfig(apiInfo, "appSecret");
        String accessToken = this.getRequiredConfig(apiInfo, "accessToken");
        // 实例化client
        ApiClient client = new DefaultApiClient();
        // 构建请求对象
        ApiRequest request = new ApiRequest.Builder(url, appKey, appSecret)
                .biz(biz).build();
        ApiResponse response = null;
        // 执行接口调用
        try {
            response = client.execute(request, accessToken);
            return response;
        } catch (Exception e) {
            log.error("聚水潭接口调用失败 | urlKey={} | biz={}", urlKey, biz, e);
            throw new ServiceException(1002, "接口调用失败: " + e.getMessage());
        } finally {
            String resultMsg = response != null ? response.getMsg() : "NULL_RESPONSE";
            bizErrorLogService.createBizErrorLog("fx", "JuShuiTanApiExecute", urlKey,
                    buildLogContext(request, response), resultMsg);
        }
    }

    // 新增私有方法
    private String getRequiredConfig(Map<String, String> config, String key) {
        String value = config.get(key);
        if (StrUtil.isBlank(value)) {
            throw new ServiceException(1001, "缺失必要配置项: " + key);
        }
        return value;
    }

    private Map<String, Object> buildLogContext(ApiRequest request, ApiResponse response) {
        Map<String, Object> context = new HashMap<>(4);
        context.put("requestUrl", request.getUrl());
        context.put("requestBiz", request.getBiz());
        context.put("responseCode", response != null ? response.getCode() : null);
        context.put("responseBody", response != null ? response.getBody() : null);
        return context;
    }
}
