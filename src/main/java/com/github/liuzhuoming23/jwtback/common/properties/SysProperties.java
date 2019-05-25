package com.github.liuzhuoming23.jwtback.common.properties;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 自定义配置
 *
 * @author liuzhuoming
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sys")
public class SysProperties {

    private String anonUrl;
    private String sensitiveField;

    public List<String> getAnonUrls() {
        return Arrays.stream(this.anonUrl.split(",")).map(String::trim)
            .collect(Collectors.toList());
    }

    public List<String> getSensitiveFields() {
        return Arrays.stream(this.sensitiveField.split(",")).map(String::trim)
            .collect(Collectors.toList());
    }

}
