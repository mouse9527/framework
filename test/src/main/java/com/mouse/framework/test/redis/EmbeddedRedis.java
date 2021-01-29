package com.mouse.framework.test.redis;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EmbeddedRedis {
    private static final Map<String, EmbeddedRedis> CACHE = new ConcurrentHashMap<>();
    private final RedisContainer container;
    private final Integer port;

    private EmbeddedRedis(String image, Integer port) {
        this.port = port;
        this.container = new RedisContainer(image)
                .withExposedPorts(this.port)
                .waitingFor(Wait.forListeningPort());
    }

    public static EmbeddedRedis getInstance(String image, Integer port) {
        String key = String.format("%s-%s", image, port);
        EmbeddedRedis embeddedRedis = EmbeddedRedis.CACHE.get(key);
        if (embeddedRedis == null) {
            embeddedRedis = new EmbeddedRedis(image, port);
            embeddedRedis.start();
            CACHE.put(key, embeddedRedis);
        }
        return embeddedRedis;
    }

    public RedisProperties getProperties() {
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost(container.getHost());
        redisProperties.setPort(container.getMappedPort(port));
        return redisProperties;
    }

    private void start() {
        this.container.start();
    }

    private static class RedisContainer extends GenericContainer<RedisContainer> {
        RedisContainer(String image) {
            super(image);
        }
    }
}
