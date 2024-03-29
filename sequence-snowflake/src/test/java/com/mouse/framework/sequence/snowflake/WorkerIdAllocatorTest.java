package com.mouse.framework.sequence.snowflake;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
class WorkerIdAllocatorTest {
    @Resource
    private RedisTemplate<String, Long> redisTemplate;
    @Resource
    private WorkerIdAllocator workerIdAllocator;
    @Resource
    private WorkerIdProperties workerIdProperties;

    @BeforeEach
    void setUp() {
        recycleAll();
    }

    private void recycleAll() {
        Optional.ofNullable(redisTemplate.keys("WorkerId:*")).orElse(Collections.emptySet())
                .forEach(key -> workerIdAllocator.recycle(Long.parseLong(key.split(":")[1])));
    }

    @AfterEach
    void tearDown() {
        recycleAll();
    }

    @Test
    void should_be_able_to_allocate_worker_id() {
        long workerId = workerIdAllocator.allocate();

        assertThat(workerId).isEqualTo(0);

        assertThat(redisTemplate.opsForValue().get("WorkerId:0")).isEqualTo(1);
        assertThat(redisTemplate.getExpire("WorkerId:0", TimeUnit.SECONDS)).isBetween(9L, 10L);

        workerIdAllocator.recycle(workerId);
    }

    @Test
    void should_be_able_to_allocate_usable_work_id() {
        long workerId = workerIdAllocator.allocate();

        assertThat(workerId).isEqualTo(0L);

        assertThat(redisTemplate.opsForValue().get("WorkerId:0")).isEqualTo(1);
        assertThat(redisTemplate.getExpire("WorkerId:0", TimeUnit.SECONDS)).isBetween(9L, 10L);

        redisTemplate.opsForValue().set("WorkerId:1", 1L);
        redisTemplate.opsForValue().set("WorkerId:2", 1L);
        redisTemplate.opsForValue().set("WorkerId:3", 1L);
        redisTemplate.opsForValue().set("WorkerId:5", 1L);

        long allocate = workerIdAllocator.allocate();
        assertThat(allocate).isEqualTo(4);

        assertThat(redisTemplate.opsForValue().get("WorkerId:4")).isEqualTo(1);
        assertThat(redisTemplate.getExpire("WorkerId:4", TimeUnit.SECONDS)).isBetween(9L, 10L);

        workerIdAllocator.recycle(workerId);
        workerIdAllocator.recycle(allocate);
    }

    @Test
    void should_be_able_to_raise_exception_when_worker_id_exhausted() {
        WorkerIdAllocator workerIdAllocator = new RedisWorkerIdAllocator(redisTemplate, 10, workerIdProperties);

        LongStream.range(0, 10).forEach(i -> redisTemplate.opsForValue().set(workerIdProperties.createKey(i), 1L));

        Throwable throwable = catchThrowable(workerIdAllocator::allocate);

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(IllegalStateException.class);
        assertThat(throwable).hasMessage("WorkerId is exhausted!!!");
    }

    private String createKey(Long workerId) {
        return workerIdProperties.createKey(workerId);
    }

    @Test
    void should_be_able_to_heartbeat_worker_id() throws InterruptedException {
        long workerId = workerIdAllocator.allocate();

        String key = createKey(workerId);
        assertThat(redisTemplate.opsForValue().get(key)).isEqualTo(1L);
        assertThat(redisTemplate.getExpire(key, TimeUnit.SECONDS)).isBetween(9L, 10L);
        Thread.sleep(2300);
        assertThat(redisTemplate.opsForValue().get(key)).isEqualTo(3L);
        assertThat(redisTemplate.getExpire(key, TimeUnit.SECONDS)).isBetween(9L, 10L);

        workerIdAllocator.recycle(workerId);
    }

    @Test
    void should_be_able_to_allocate_worker_id_in_multithreading() throws InterruptedException {
        Set<Long> workerIds = Collections.synchronizedSet(new HashSet<>());
        int count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                long allocate = workerIdAllocator.allocate();
                workerIds.add(allocate);
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();

        assertThat(workerIds).hasSize(count);
    }
}
