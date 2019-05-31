package com.github.liuzhuoming23.vegetable.admin.common.redis;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis操作
 *
 * @author liuzhuoming
 */
@Component
public class RedisOperation {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public ValueOperation value() {
        return new ValueOperation(redisTemplate);
    }

    public HashOperation hash() {
        return new HashOperation(redisTemplate);
    }

    public SetOperation set() {
        return new SetOperation(redisTemplate);
    }

    /**
     * 删除元素
     *
     * @param key redis key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置过期时间
     *
     * @param key redis key
     * @param date expire date
     */
    public void expire(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }
}
