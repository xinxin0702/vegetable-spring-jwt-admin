package com.github.liuzhuoming23.jwtback.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * redis设置
 *
 * @author liuzhuoming
 */
@Configuration
public class RedisConfig {

    /**
     * 设置redis缓存为json格式
     *
     * @param connectionFactory connectionFactory
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        Jackson2JsonRedisSerializer<Object> redisSerializer = new Jackson2JsonRedisSerializer<>(
            Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        redisSerializer.setObjectMapper(objectMapper);
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration
            .defaultCacheConfig()
            .serializeValuesWith(RedisSerializationContext
                .SerializationPair
                .fromSerializer(redisSerializer))
            .entryTtl(Duration.ofSeconds(60 * 60 * 1000L));
        RedisCacheManager redisCacheManager = RedisCacheManager
            .builder(connectionFactory)
            .cacheDefaults(cacheConfiguration)
            .build();
        return redisCacheManager;
    }
}
