package com.mouse.framework.sequence.snowflake;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class RedisWorkerIdAllocator implements WorkerIdAllocator {
    public static final int BASE = 1000;
    private final RedisTemplate<String, Long> redisTemplate;
    private final long maxEffectiveSeconds;
    private final Timer timer;
    private final SnowFlakeProperties.WorkerIdProperties properties;

    public RedisWorkerIdAllocator(RedisTemplate<String, Long> redisTemplate, SnowFlakeProperties.WorkerIdProperties properties) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
        this.maxEffectiveSeconds = properties.getHeartbeatIntervalSeconds() * properties.getMaxFailedTimes();
        timer = new Timer();
    }

    @Override
    public long allocate(long maxWorkerId) {
        int id = -1; // TODO
        for (int i = 0; i < maxWorkerId; i++) {
            Boolean exists = redisTemplate.hasKey(String.format("%s:%d", properties.getKeyPrefix(), i));
            if (exists != null && !exists) {
                id = i;
                break;
            }
        }

        final String key = String.format("%s:%d", properties.getKeyPrefix(), id);
        redisTemplate.opsForValue().set(key, 1L);
        redisTemplate.expire(key, maxEffectiveSeconds, TimeUnit.SECONDS);
        long interval = properties.getHeartbeatIntervalSeconds() * BASE;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Long count = redisTemplate.opsForValue().get(key);
                if (count == null) {
                    this.cancel();
                    return;
                }
                redisTemplate.opsForValue().set(key, count + 1);
                redisTemplate.expire(key, maxEffectiveSeconds, TimeUnit.SECONDS);
            }
        };
        timer.schedule(task, interval, interval);
        return id;
    }
}
