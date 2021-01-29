package com.mouse.framework.test.redis;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@SuppressWarnings("ConfigurationProperties")
@ConfigurationProperties(prefix = "application.embedded.redis")
public class EmbeddedRedisProperties {
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
