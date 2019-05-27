package com.github.liuzhuoming23.svea.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis设置
 *
 * @author liuzhuoming
 */
@Configuration
@MapperScan(value = {"com.github.liuzhuoming23.svea.*.mapper"})
public class MybatisConfig {

}
