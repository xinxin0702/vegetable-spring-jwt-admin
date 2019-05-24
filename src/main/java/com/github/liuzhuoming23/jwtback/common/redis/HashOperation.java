package com.github.liuzhuoming23.jwtback.common.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis hash操作
 *
 * @author liuzhuoming
 */
public class HashOperation {

    private StringRedisTemplate stringRedisTemplate;

    public HashOperation(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 添加hash数据
     *
     * @param key redis key
     * @param hashKey hash key
     * @param val value
     */
    public void put(String key, String hashKey, String val) {
        stringRedisTemplate.opsForHash().put(key, hashKey, val);
    }

    /**
     * 获取hash数据
     *
     * @param key redis key
     * @param hashKey hash key
     */
    public String get(String key, String hashKey) {
        return (String) stringRedisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 删除hash数据
     *
     * @param key redis key
     * @param hashKeys hash keys
     */
    public void delete(String key, String... hashKeys) {
        stringRedisTemplate.opsForHash().delete(key, (Object[]) hashKeys);
    }
}
