package com.github.liuhzuoming23.jwtback.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis操作
 *
 * @author: liuzhuoming
 * @date: 2018/7/2 10:38
 */
@Component
public class RedisOperation {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ValueOperation value() {
        return new ValueOperation(stringRedisTemplate);
    }

    public HashOperation hash() {
        return new HashOperation(stringRedisTemplate);
    }
}
