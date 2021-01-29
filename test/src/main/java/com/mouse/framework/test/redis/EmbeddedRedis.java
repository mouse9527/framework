package com.mouse.framework.test.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public final class EmbeddedRedis implements InitializingBean, DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RedisContainer container;
    private final EmbeddedRedisProperties properties;

    public EmbeddedRedis(EmbeddedRedisProperties properties) {
        this.container = new RedisContainer(properties.getImage())
                .withExposedPorts(properties.getPort())
                .waitingFor(Wait.forListeningPort());
        this.properties = properties;
    }

    @Override
    public void destroy() {
        this.container.stop();
        logger.info("EmbeddedRedis closed!");
    }

    @Override
    public void afterPropertiesSet() {
        this.container.start();
    }

    public RedisProperties getProperties() {
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost(container.getHost());
        redisProperties.setPort(container.getMappedPort(properties.getPort()));
        return redisProperties;
    }

    static class RedisContainer extends GenericContainer<RedisContainer> {
        RedisContainer(String image) {
            super(image);
        }
    }
}
