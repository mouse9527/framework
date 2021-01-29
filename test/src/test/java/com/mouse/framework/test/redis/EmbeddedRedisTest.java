package com.mouse.framework.test.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmbeddedRedisTest {
    public static final String KEY = "x";
    public static final String VALUE = "xx";
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void should_be_able_to_start_redis_template() {
        redisTemplate.opsForValue().set(KEY, VALUE);

        assertThat(redisTemplate.opsForValue().get(KEY)).isEqualTo(VALUE);

        redisTemplate.delete(KEY);
    }
}
