package com.github.liuzhuoming23.jwtback.common.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis value操作
 *
 * @author gnimouhzuil
 * @date 2019/5/23 17:12
 */
public class ValueOperation {

    private StringRedisTemplate stringRedisTemplate;

    public ValueOperation(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 添加数据
     *
     * @param key redis key
     * @param val values
     */
    public void set(String key, String val) {
        stringRedisTemplate.opsForValue().set(key, val);
    }

    /**
     * 获取数据
     *
     * @param key redis key
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除数据
     *
     * @param key redis key
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
}
