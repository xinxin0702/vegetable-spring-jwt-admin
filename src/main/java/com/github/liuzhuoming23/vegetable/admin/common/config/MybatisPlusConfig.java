package com.github.liuzhuoming23.vegetable.admin.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus设置
 *
 * @author liuzhuoming
 */
@Configuration
@MapperScan(value = {"com.github.liuzhuoming23.vegetable.admin.*.mapper"})
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
