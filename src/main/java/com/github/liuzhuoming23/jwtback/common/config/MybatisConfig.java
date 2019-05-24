package com.github.liuzhuoming23.jwtback.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis设置
 *
 * @author liuzhuoming
 */
@Configuration
@MapperScan(value = {"com.github.liuzhuoming23.jwtback.*.mapper"})
public class MybatisConfig {

}
