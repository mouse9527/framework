package com.mouse.framework.test.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.concurrent.atomic.AtomicInteger;

public final class EmbeddedRedis {
    private static final AtomicInteger START_TIMES = new AtomicInteger();
    private static EmbeddedRedis instance;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RedisContainer container;
    private final EmbeddedRedisProperties properties;

    public EmbeddedRedis(EmbeddedRedisProperties properties) {
        this.container = new RedisContainer(properties.getImage())
                .withExposedPorts(properties.getPort())
                .waitingFor(Wait.forListeningPort());
        this.properties = properties;
    }

    public static Integer startTimes() {
        return START_TIMES.get();
    }


    public static EmbeddedRedis getInstance() {
        if (instance == null) {
            synchronized (EmbeddedRedis.class) {
                if (instance == null) {
                    EmbeddedRedis instance = new EmbeddedRedis(new EmbeddedRedisProperties());
                    instance.init();
                    EmbeddedRedis.instance = instance;
                }
            }
        }
        return instance;
    }

    public static void close() {
        EmbeddedRedis.instance.stop();
    }

    public static Object create() {
        START_TIMES.incrementAndGet();
        return getInstance();
    }

    public static EmbeddedRedis get() {
        return EmbeddedRedis.instance;
    }

    private void stop() {
        this.container.stop();
        logger.info("EmbeddedRedis closed!");
    }

    private void init() {
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
