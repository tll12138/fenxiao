package cn.iocoder.yudao.module.fx.service.jushuitanapi;

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

    @Override
    public ApiResponse execute(String urlKey, String biz) {
        //从数据字典获取接口数据
        Map<String, String> apiInfo = dictDataService.getDictDataMapByDictType("fx_jushuitan_API_info");
        if (MapUtils.isEmpty(apiInfo)) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        String url = apiInfo.get(urlKey);
        String appKey = apiInfo.get("appKey");
        String appSecret = apiInfo.get("appSecret");
        String accessToken = apiInfo.get("accessToken");
        // 实例化client
        ApiClient client = new DefaultApiClient();
        // 构建请求对象
        ApiRequest request = new ApiRequest.Builder(url, appKey, appSecret)
                .biz(biz).build();
        // 执行接口调用
        try {
            return client.execute(request, accessToken);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
