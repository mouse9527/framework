package com.mouse.framework.test.redis;

import lombok.Generated;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Generated
@Configuration
@EnableAutoConfiguration
@AutoConfigureBefore(name = "org.springframework.boot.autoconfigure.data.redis.RedisConnectionConfiguration")
@ConfigurationProperties(prefix = "application.embedded.redis")
public class EmbeddedRedisConfiguration {
    private static final String DEFAULT_IMAGE = "redis";
    private String image;

    public void setImage(String image) {
        this.image = image;
    }

    @Bean
    @Primary
    public RedisProperties redisProperties(EmbeddedRedis embeddedRedis) {
        return embeddedRedis.getProperties();
    }


    @Bean
    public EmbeddedRedis embeddedRedis() {
        return EmbeddedRedis.getInstance(image == null ? DEFAULT_IMAGE : image);
    }
}
