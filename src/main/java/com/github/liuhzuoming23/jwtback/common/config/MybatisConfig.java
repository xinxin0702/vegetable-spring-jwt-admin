package com.github.liuhzuoming23.jwtback.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuzhuoming
 */
@Configuration
@MapperScan(value = {"com.github.liuhzuoming23.jwtback.*.mapper"})
public class MybatisConfig {

}
