package com.mouse.framework.sequence.snowflake;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WorkerIdAllocatorTest {
    public static final long MAX_WORKER_ID = 1204;
    @Resource
    private RedisTemplate<String, Long> redisTemplate;
    @Resource
    private WorkerIdAllocator workerIdAllocator;

    @AfterEach
    void tearDown() {
        //noinspection ConstantConditions
        redisTemplate.keys("WorkerId:*").forEach(redisTemplate::delete);
    }

    @Test
    void should_be_able_to_allocate_worker_id() {
        long workerId = workerIdAllocator.allocate(MAX_WORKER_ID);

        assertThat(workerId).isEqualTo(0);

        assertThat(redisTemplate.opsForValue().get("WorkerId:0")).isEqualTo(1);
        assertThat(redisTemplate.getExpire("WorkerId:0", TimeUnit.SECONDS)).isBetween(9L, 10L);
    }

    @Test
    void should_be_able_to_allocate_usable_work_id() {
        redisTemplate.opsForValue().set("WorkerId:0", 1L);

        long workerId = workerIdAllocator.allocate(MAX_WORKER_ID);

        assertThat(workerId).isEqualTo(1);

        assertThat(redisTemplate.opsForValue().get("WorkerId:1")).isEqualTo(1);
        assertThat(redisTemplate.getExpire("WorkerId:1", TimeUnit.SECONDS)).isBetween(9L, 10L);

        redisTemplate.opsForValue().set("WorkerId:2", 1L);
        redisTemplate.opsForValue().set("WorkerId:3", 1L);
        redisTemplate.opsForValue().set("WorkerId:5", 1L);

        assertThat(workerIdAllocator.allocate(MAX_WORKER_ID)).isEqualTo(4);

        assertThat(redisTemplate.opsForValue().get("WorkerId:4")).isEqualTo(1);
        assertThat(redisTemplate.getExpire("WorkerId:4", TimeUnit.SECONDS)).isBetween(9L, 10L);
    }

}
