package com.github.liuzhuoming23.jwtback.common.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis set操作
 *
 * @author liuzhuoming
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

    /**
     * 获取set size
     *
     * @param key redis key
     */
    public Long size(String key) {
        return stringRedisTemplate.opsForSet().size(key);
    }
}
