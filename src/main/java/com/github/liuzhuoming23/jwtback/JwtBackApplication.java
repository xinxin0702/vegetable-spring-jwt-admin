package com.github.liuzhuoming23.jwtback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * springboot启动类
 *
 * @author liuzhuoming
 */
@SpringBootApplication
@EnableCaching
public class JwtBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtBackApplication.class, args);
    }

}