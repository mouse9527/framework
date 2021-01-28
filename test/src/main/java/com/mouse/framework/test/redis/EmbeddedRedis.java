package com.mouse.framework.test.redis;

import lombok.Generated;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@Generated
public final class EmbeddedRedis {
    public static final int PORT = 6379;
    private static final Object LOCK = new Object();
    private static EmbeddedRedis instance;
    private final RedisContainer container;

    private EmbeddedRedis(String imageName) {
        this.container = new RedisContainer(imageName)
                .withExposedPorts(PORT)
                .waitingFor(
                        Wait.forListeningPort()
                );
        this.container.start();
    }

    public static EmbeddedRedis getInstance(String mongoDockerImageName) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new EmbeddedRedis(mongoDockerImageName);
                }
            }
        }
        return instance;
    }

    public void stop() {
        container.stop();
        EmbeddedRedis.instance = null;
    }


    public RedisProperties getProperties() {
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost(container.getHost());
        redisProperties.setPort(container.getMappedPort(PORT));
        return redisProperties;
    }

    static class RedisContainer extends GenericContainer<RedisContainer> {
        RedisContainer(String image) {
            super(image);
        }
    }

}
