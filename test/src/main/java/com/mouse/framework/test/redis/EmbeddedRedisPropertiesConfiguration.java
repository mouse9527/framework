package com.mouse.framework.test.redis;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@AutoConfigureBefore(name = "org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration")
@EnableConfigurationProperties(EmbeddedRedisProperties.class)
public class EmbeddedRedisPropertiesConfiguration {
    @Bean
    @Primary
    public RedisProperties redisProperties(EmbeddedRedis embeddedRedis) {
        return embeddedRedis.getProperties();
    }

    @Bean
    public EmbeddedRedis embeddedRedis(EmbeddedRedisProperties properties) {
        return new EmbeddedRedis(properties);
    }
}
