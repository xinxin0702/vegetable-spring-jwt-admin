package com.github.liuhzuoming23.jwtback.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sys")
public class SysProperties {

    private String anonUrl;
}
