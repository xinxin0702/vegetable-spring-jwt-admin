package com.github.liuzhuoming23.jwtback.common.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis set操作
 *
 * @author liuzhuoming
 */
public class SetOperation {

    private StringRedisTemplate stringRedisTemplate;

    SetOperation(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 添加set元素
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

    /**
     * 移除set元素
     *
     * @param key redis key
     * @param vals values
     */
    public void remove(String key, String... vals) {
        stringRedisTemplate.opsForSet().remove(key, (Object[]) vals);
    }

    /**
     * 值是否存在set中
     *
     * @param key redis key
     * @param val value
     */
    public Boolean member(String key, String val) {
        return stringRedisTemplate.opsForSet().isMember(key, val);
    }

    /**
     * 随机移除并返回set中一个元素
     *
     * @param key redis key
     */
    public String pop(String key) {
        return stringRedisTemplate.opsForSet().pop(key);
    }
}
