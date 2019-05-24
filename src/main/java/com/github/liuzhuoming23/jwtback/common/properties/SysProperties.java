package com.github.liuzhuoming23.jwtback.common.properties;

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
}
