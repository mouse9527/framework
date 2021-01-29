package com.mouse.framework.test.redis;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EmbeddedRedis {
    private static final Map<String, EmbeddedRedis> CACHE = new ConcurrentHashMap<>();
    private final RedisContainer container;
    private final EmbeddedRedisPropertiesConfiguration.EmbeddedRedisProperties properties;

    public EmbeddedRedis(EmbeddedRedisPropertiesConfiguration.EmbeddedRedisProperties properties) {
        this.container = new RedisContainer(properties.getImage())
                .withExposedPorts(properties.getPort())
                .waitingFor(Wait.forListeningPort());
        this.properties = properties;
    }

    public static EmbeddedRedis getInstance(EmbeddedRedisPropertiesConfiguration.EmbeddedRedisProperties properties) {
        String key = String.format("%s-%s", properties.getImage(), properties.getPort());
        EmbeddedRedis embeddedRedis = EmbeddedRedis.CACHE.get(key);
        if (embeddedRedis == null) {
            embeddedRedis = new EmbeddedRedis(properties);
            embeddedRedis.start();
            CACHE.put(key, embeddedRedis);
        }
        return embeddedRedis;
    }

    public RedisProperties getProperties() {
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost(container.getHost());
        redisProperties.setPort(container.getMappedPort(properties.getPort()));
        return redisProperties;
    }

    public void start() {
        this.container.start();
    }

    private static class RedisContainer extends GenericContainer<RedisContainer> {
        RedisContainer(String image) {
            super(image);
        }
    }
}
