package com.mouse.framework.sequence.snowflake;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
class WorkerIdAllocatorTest {
    public static final long MAX_WORKER_ID = 1204;
    @Resource
    private RedisTemplate<String, Long> redisTemplate;
    @Resource
    private WorkerIdAllocator workerIdAllocator;
    @Resource
    private SnowFlakeProperties snowFlakeProperties;

    @BeforeEach
    void setUp() {
        //noinspection ConstantConditions
        redisTemplate.delete(redisTemplate.keys("WorkerId:*"));
    }

    @AfterEach
    void tearDown() {
        //noinspection ConstantConditions
        redisTemplate.delete(redisTemplate.keys("WorkerId:*"));
    }

    @Test
    void should_be_able_to_allocate_worker_id() {
        long workerId = workerIdAllocator.allocate(MAX_WORKER_ID);

        assertThat(workerId).isEqualTo(0);

        assertThat(redisTemplate.opsForValue().get("WorkerId:0")).isEqualTo(1);
        assertThat(redisTemplate.getExpire("WorkerId:0", TimeUnit.SECONDS)).isBetween(9L, 10L);

        workerIdAllocator.recycle(workerId);
    }

    @Test
    void should_be_able_to_allocate_usable_work_id() {
        long workerId = workerIdAllocator.allocate(MAX_WORKER_ID);

        assertThat(workerId).isEqualTo(0L);

        assertThat(redisTemplate.opsForValue().get("WorkerId:0")).isEqualTo(1);
        assertThat(redisTemplate.getExpire("WorkerId:0", TimeUnit.SECONDS)).isBetween(9L, 10L);

        redisTemplate.opsForValue().set("WorkerId:1", 1L);
        redisTemplate.opsForValue().set("WorkerId:2", 1L);
        redisTemplate.opsForValue().set("WorkerId:3", 1L);
        redisTemplate.opsForValue().set("WorkerId:5", 1L);

        long allocate = workerIdAllocator.allocate(MAX_WORKER_ID);
        assertThat(allocate).isEqualTo(4);

        assertThat(redisTemplate.opsForValue().get("WorkerId:4")).isEqualTo(1);
        assertThat(redisTemplate.getExpire("WorkerId:4", TimeUnit.SECONDS)).isBetween(9L, 10L);

        workerIdAllocator.recycle(workerId);
        workerIdAllocator.recycle(allocate);
    }

    @Test
    void should_be_able_to_raise_exception_when_worker_id_exhausted() {
        LongStream.range(0, 10).forEach(i -> redisTemplate.opsForValue().set(createKey(i), 1L));

        Throwable throwable = catchThrowable(() -> workerIdAllocator.allocate(10));

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(IllegalStateException.class);
        assertThat(throwable).hasMessage("WorkerId is exhausted!!!");
    }

    private String createKey(Long workerId) {
        return snowFlakeProperties.getWorkerId().createKey(workerId);
    }

    @Test
    void should_be_able_to_heartbeat_worker_id() throws InterruptedException {
        long workerId = workerIdAllocator.allocate(MAX_WORKER_ID);

        String key = createKey(workerId);
        assertThat(redisTemplate.opsForValue().get(key)).isEqualTo(1L);
        assertThat(redisTemplate.getExpire(key, TimeUnit.SECONDS)).isBetween(9L, 10L);
        Thread.sleep(2300);
        assertThat(redisTemplate.opsForValue().get(key)).isEqualTo(3L);
        assertThat(redisTemplate.getExpire(key, TimeUnit.SECONDS)).isBetween(9L, 10L);

        workerIdAllocator.recycle(workerId);
    }
}
