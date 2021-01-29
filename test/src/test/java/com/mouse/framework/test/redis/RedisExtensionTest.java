package com.mouse.framework.test.redis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(RedisExtension.class)
public class RedisExtensionTest {

    @Test
    void should_be_able_to_create_mongo_container_once() {
        assertThat(EmbeddedRedis.startTimes()).isEqualTo(1);
        RedisProperties properties = EmbeddedRedis.get().getProperties();
        assertThat(properties).isNotNull();
        assertThat(properties.getHost()).isNotNull();
        assertThat(properties.getPort()).isNotNull();
    }
}

@ExtendWith(RedisExtension.class)
class RedisExtensionRepeatTest {

    @Test
    void should_be_able_to_create_mongo_container_once() {
        assertThat(EmbeddedRedis.startTimes()).isEqualTo(1);
        RedisProperties properties = EmbeddedRedis.get().getProperties();
        assertThat(properties).isNotNull();
        assertThat(properties.getHost()).isNotNull();
        assertThat(properties.getPort()).isNotNull();
    }
}
