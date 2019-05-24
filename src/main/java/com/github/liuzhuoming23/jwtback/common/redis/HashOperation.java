package com.github.liuzhuoming23.jwtback.common.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis hash操作
 *
 * @author liuzhuoming
 */
public class HashOperation {

    private StringRedisTemplate stringRedisTemplate;

    HashOperation(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 添加hash元素
     *
     * @param key redis key
     * @param field hash field
     * @param val value
     */
    public void put(String key, String field, String val) {
        stringRedisTemplate.opsForHash().put(key, field, val);
    }

    /**
     * 获取hash元素
     *
     * @param key redis key
     * @param field hash field
     */
    public String get(String key, String field) {
        return (String) stringRedisTemplate.opsForHash().get(key, field);
    }

    /**
     * 删除hash元素
     *
     * @param key redis key
     * @param fields hash fields
     */
    public void delete(String key, String... fields) {
        stringRedisTemplate.opsForHash().delete(key, (Object[]) fields);
    }
}
