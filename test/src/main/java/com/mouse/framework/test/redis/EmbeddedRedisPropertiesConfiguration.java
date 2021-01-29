package com.mouse.framework.test.redis;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConditionalOnBean(EmbeddedRedis.class)
@AutoConfigureBefore(name = "org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration")
public class EmbeddedRedisPropertiesConfiguration {

    @Bean
    @Primary
    public RedisProperties redisProperties(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                                   EmbeddedRedis embeddedRedis) {
        return embeddedRedis.getProperties();
    }
}
