package cn.iocoder.yudao.module.fx.service.utils;

import cn.iocoder.yudao.module.fx.controller.admin.utils.vo.AnalyzeAddressVo;
import cn.iocoder.yudao.module.fx.utils.FxProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zrl
 * @date 2024/7/26
 */
@Service
@Validated
public class UtilsServiceImpl implements UtilsService {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private FxProperties fxProperties;

    @Override
    public AnalyzeAddressVo analyzeAddress(String text) {
        // 1.1 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 1.3 构建请求参数
        Map<String, Object> body = new HashMap<>();
        body.put("text", text);

//         2. 执行请求
        ResponseEntity<AnalyzeAddressVo> exchange = restTemplate.exchange(
                fxProperties.getAnalyzeAddressUrl(),
                HttpMethod.POST, new HttpEntity<>(body, headers),
                new ParameterizedTypeReference<AnalyzeAddressVo>() {
                }); // 解决泛型丢失
        Assert.isTrue(exchange.getStatusCode().is2xxSuccessful(), "响应必须是 200 成功");
        return exchange.getBody();
    }
}