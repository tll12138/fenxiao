package cn.iocoder.yudao.module.fx.utils.config;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * @author zrl
 * @date 2024/7/26
 */
@ConfigurationProperties(prefix = "fx")
@Configuration
@Validated
@Data
public class FxProperties {

    /**
     * 一键解析地址
     */
    @NotEmpty(message = "一键解析地址API的URL不能为空")
    private String analyzeAddressUrl;
}
