package com.mouse.framework.test.redis;

import lombok.Setter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(RedisAutoConfiguration.class)
@EnableConfigurationProperties(EmbeddedRedisPropertiesConfiguration.EmbeddedRedisProperties.class)
@ConditionalOnProperty(value = "test.embedded.redis.enable", havingValue = "true")
public class EmbeddedRedisPropertiesConfiguration {
    @Bean
    @Primary
    public RedisProperties redisProperties(EmbeddedRedisProperties properties) {
        return EmbeddedRedis.getInstance(properties).getProperties();
    }

    @Setter
    @ConfigurationProperties(prefix = "test.embedded.redis")
    public static class EmbeddedRedisProperties {
        private static final String DEFAULT_IMAGE = "redis";
        private static final Integer DEFAULT_PORT = 6379;
        private String image;
        private Integer port;

        public String getImage() {
            return image == null ? DEFAULT_IMAGE : image;
        }

        public Integer getPort() {
            return port == null ? DEFAULT_PORT : port;
        }
    }
}
