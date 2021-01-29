package com.mouse.framework.test.redis;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@AutoConfigureBefore(name = "org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration")
public class EmbeddedRedisPropertiesConfiguration {
    @Bean
    @Primary
    public RedisProperties redisProperties() {
        return EmbeddedRedis.get().getProperties();
    }
}
