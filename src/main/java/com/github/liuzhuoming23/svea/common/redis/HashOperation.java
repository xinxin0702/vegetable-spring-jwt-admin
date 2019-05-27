package com.github.liuzhuoming23.svea.common.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis hash操作
 *
 * @author liuzhuoming
 */
public class HashOperation {

    private StringRedisTemplate redisTemplate;

    HashOperation(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 添加hash元素
     *
     * @param key redis key
     * @param field hash field
     * @param val value
     */
    public void put(String key, String field, String val) {
        redisTemplate.opsForHash().put(key, field, val);
    }

    /**
     * 获取hash元素
     *
     * @param key redis key
     * @param field hash field
     */
    public String get(String key, String field) {
        return redisTemplate.<String, String>opsForHash().get(key, field);
    }

    /**
     * 删除hash元素
     *
     * @param key redis key
     * @param fields hash fields
     */
    public void delete(String key, String... fields) {
        redisTemplate.<String, String>opsForHash().delete(key, (Object[]) fields);
    }

    /**
     * hash元素值加一
     *
     * @param key redis key
     * @param field hash field
     */
    public void increment(String key, String field) {
        redisTemplate.<String, Integer>opsForHash().increment(key, field, 1);
    }
}
