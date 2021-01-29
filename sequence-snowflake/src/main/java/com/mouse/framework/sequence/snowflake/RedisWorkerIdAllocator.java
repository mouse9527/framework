package com.mouse.framework.sequence.snowflake;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class RedisWorkerIdAllocator implements WorkerIdAllocator {
    private final RedisTemplate<String, Long> redisTemplate;
    private final SnowFlakeProperties.WorkerIdProperties properties;
    private final Timer timer;
    private final List<TimerTask> heartbeats;

    public RedisWorkerIdAllocator(RedisTemplate<String, Long> redisTemplate, SnowFlakeProperties.WorkerIdProperties properties) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
        this.timer = new Timer();
        heartbeats = new ArrayList<>();
    }

    @Override
    public synchronized long allocate(long maxWorkerId) {
        final long workerId = getUsableWorkerId(maxWorkerId);
        final String key = properties.createKey(workerId);
        long maxEffectiveSeconds = properties.getMaxEffectiveSeconds(properties);

        redisTemplate.opsForValue().set(key, 1L);
        redisTemplate.expire(key, maxEffectiveSeconds, TimeUnit.SECONDS);

        TimerTask workerIdHeartbeat = new WorkerIdHeartbeat(key, maxEffectiveSeconds, redisTemplate);
        long heartBeatIntervalMillisecond = properties.getHeartBeatIntervalMillisecond();
        timer.schedule(workerIdHeartbeat, heartBeatIntervalMillisecond, heartBeatIntervalMillisecond);
        heartbeats.add(workerIdHeartbeat);
        return workerId;
    }

    private long getUsableWorkerId(long maxWorkerId) {
        for (int i = 0; i < maxWorkerId; i++) {
            if (this.usable(i)) return i;
        }
        throw new IllegalStateException("WorkerId is exhausted!!!");
    }

    @Override
    public synchronized void recycle(long workerId) {
        redisTemplate.delete(properties.createKey(workerId));
        heartbeats.forEach(TimerTask::cancel);
    }

    private boolean usable(long workerId) {
        Boolean exists = redisTemplate.hasKey(properties.createKey(workerId));
        return exists != null && !exists;
    }

    private static class WorkerIdHeartbeat extends TimerTask {
        private final String key;
        private final RedisTemplate<String, Long> redisTemplate;
        private final Long maxEffectiveSeconds;

        WorkerIdHeartbeat(String key, Long maxEffectiveSeconds, RedisTemplate<String, Long> redisTemplate) {
            this.key = key;
            this.redisTemplate = redisTemplate;
            this.maxEffectiveSeconds = maxEffectiveSeconds;
        }

        @Override
        public void run() {
            Long heartbeatCount = redisTemplate.opsForValue().get(key);
            if (heartbeatCount == null) {
                throw new IllegalStateException("WorkerId timeout!!!");
            }
            redisTemplate.opsForValue().set(key, heartbeatCount + 1);
            redisTemplate.expire(key, maxEffectiveSeconds, TimeUnit.SECONDS);
        }
    }
}
