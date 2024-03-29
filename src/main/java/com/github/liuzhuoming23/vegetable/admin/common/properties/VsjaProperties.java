package com.github.liuzhuoming23.vegetable.admin.common.properties;

import com.github.liuzhuoming23.vegetable.admin.common.exception.VsjaException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 自定义配置
 *
 * @author liuzhuoming
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "v-sja")
public class VsjaProperties {

    private String anonUrl;
    private String sensitiveField;
    private String logLevel;

    public List<String> getAnonUrls() {
        return Arrays.stream(this.anonUrl.split(",")).map(String::trim)
            .collect(Collectors.toList());
    }

    public List<String> getSensitiveFields() {
        return Arrays.stream(this.sensitiveField.split(",")).map(String::trim)
            .collect(Collectors.toList());
    }

    public int getLogLevel() {
        if (StringUtils.isEmpty(logLevel)) {
            return 0;
        } else {
            int logLevel;
            try {
                logLevel = Integer.parseInt(this.logLevel);
            } catch (NumberFormatException e) {
                throw new VsjaException("logLevel must be number");
            }
            return logLevel;
        }
    }
}
