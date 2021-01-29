package com.mouse.framework.sequence.snowflake;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WorkerIdAllocatorTest {
    @Resource
    private RedisTemplate<String, Long> redisTemplate;
    @Resource
    private WorkerIdAllocator workerIdAllocator;

    @Test
    void should_be_able_to_allocate_worker_id() throws InterruptedException {
        long maxWorkerId = 1204;
        long workerId = workerIdAllocator.allocate(maxWorkerId);

        assertThat(workerId).isGreaterThan(-1);
        assertThat(workerId).isLessThan(maxWorkerId);

        assertThat(redisTemplate.opsForValue().get("WorkerId:0")).isEqualTo(1);
        assertThat(redisTemplate.getExpire("WorkerId:0", TimeUnit.SECONDS)).isBetween(9L, 10L);
        Thread.sleep(2000);
        assertThat(redisTemplate.opsForValue().get("WorkerId:0")).isEqualTo(3);
        assertThat(redisTemplate.getExpire("WorkerId:0", TimeUnit.SECONDS)).isBetween(9L, 10L);
    }
}
