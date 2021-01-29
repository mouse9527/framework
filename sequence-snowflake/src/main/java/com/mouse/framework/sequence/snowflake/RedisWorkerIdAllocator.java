package com.mouse.framework.sequence.snowflake;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

public class RedisWorkerIdAllocator implements WorkerIdAllocator {
    private final RedisTemplate<String, Long> redisTemplate;
    private final SnowFlakeProperties.WorkerIdProperties properties;

    public RedisWorkerIdAllocator(RedisTemplate<String, Long> redisTemplate, SnowFlakeProperties.WorkerIdProperties properties) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
    }

    @Override
    public long allocate(long maxWorkerId) {
        long workerId = LongStream.range(0, maxWorkerId)
                .filter(this::usable)
                .findFirst().orElseThrow(() -> new IllegalStateException("WorkerId is exhausted!!!"));
        final String key = properties.createKey(workerId);
        redisTemplate.opsForValue().set(key, 1L);
        redisTemplate.expire(key, properties.getMaxEffectiveSeconds(properties), TimeUnit.SECONDS);
        return workerId;
    }

    private boolean usable(long workerId) {
        Boolean exists = redisTemplate.hasKey(properties.createKey(workerId));
        return exists != null && !exists;
    }
}
