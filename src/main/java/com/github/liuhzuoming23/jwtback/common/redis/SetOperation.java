package com.github.liuhzuoming23.jwtback.common.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis set操作
 *
 * @author gnimouhzuil
 * @date 2019/5/23 23:21
 */
public class SetOperation {

    private StringRedisTemplate stringRedisTemplate;

    public SetOperation(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 添加set数据
     *
     * @param key redis key
     * @param vals values
     */
    public void add(String key, String... vals) {
        stringRedisTemplate.opsForSet().add(key, vals);
    }

}
