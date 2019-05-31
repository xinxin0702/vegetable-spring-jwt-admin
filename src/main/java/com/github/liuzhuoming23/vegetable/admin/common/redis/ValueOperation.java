package com.github.liuzhuoming23.vegetable.admin.common.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis value操作
 *
 * @author liuzhuoming
 */
public class ValueOperation {

    private StringRedisTemplate redisTemplate;

    ValueOperation(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 添加元素
     *
     * @param key redis key
     * @param val values
     */
    public void set(String key, String val) {
        redisTemplate.opsForValue().set(key, val);
    }

    /**
     * 获取元素
     *
     * @param key redis key
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 元素值加1
     *
     * @param key redis key
     */
    public void increment(String key) {
        redisTemplate.opsForValue().increment(key);
    }
}
