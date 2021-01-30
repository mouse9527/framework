package com.mouse.framework.sequence.snowflake;

import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RedisWorkerIdAllocator implements WorkerIdAllocator {
    private final RedisTemplate<String, Long> redisTemplate;
    private final SnowFlakeProperties.WorkerIdProperties properties;
    private final Timer timer;
    private final Map<Long, TimerTask> heartbeats;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RedisWorkerIdAllocator(RedisTemplate<String, Long> redisTemplate, SnowFlakeProperties.WorkerIdProperties properties) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
        this.timer = new Timer(true);
        this.heartbeats = new ConcurrentHashMap<>();
    }

    @Override
    public long allocate(long maxWorkerId) {
        final long workerId = getUsableWorkerId(maxWorkerId);
        final String key = properties.createKey(workerId);
        long maxEffectiveSeconds = properties.getMaxEffectiveSeconds();

        TimerTask workerIdHeartbeat = new WorkerIdHeartbeat(workerId, key, maxEffectiveSeconds, redisTemplate);
        long heartBeatIntervalMillisecond = properties.getHeartBeatIntervalMillisecond();
        timer.schedule(workerIdHeartbeat, heartBeatIntervalMillisecond, heartBeatIntervalMillisecond);
        heartbeats.put(workerId, workerIdHeartbeat);
        logger.info("WorkerId allocate success! workerId: [{}]", workerId);
        return workerId;
    }

    private long getUsableWorkerId(long maxWorkerId) {
        for (int i = 0; i < maxWorkerId; i++) {
            if (this.usable(i)) return i;
        }
        logger.error("WorkerId is exhausted!!!");
        throw new IllegalStateException("WorkerId is exhausted!!!");
    }

    @Override
    public void recycle(long workerId) {
        redisTemplate.delete(properties.createKey(workerId));
        Optional.ofNullable(heartbeats.get(workerId)).ifPresent(TimerTask::cancel);
    }

    @Generated
    private boolean usable(long workerId) {
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(properties.createKey(workerId),
                        1L,
                        properties.getMaxEffectiveSeconds(),
                        TimeUnit.SECONDS);
        return success != null && success;
    }

    private static class WorkerIdHeartbeat extends TimerTask {
        private final long workerId;
        private final String key;
        private final RedisTemplate<String, Long> redisTemplate;
        private final Long maxEffectiveSeconds;
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        WorkerIdHeartbeat(long workerId,
                          String key,
                          Long maxEffectiveSeconds,
                          RedisTemplate<String, Long> redisTemplate) {
            this.workerId = workerId;
            this.key = key;
            this.redisTemplate = redisTemplate;
            this.maxEffectiveSeconds = maxEffectiveSeconds;
        }

        @Override
        @Generated
        public void run() {
            try {
                Long heartbeatCount = redisTemplate.opsForValue().get(key);
                if (heartbeatCount == null) {
                    throw new IllegalStateException("WorkerId timeout!!!");
                }
                long newCount = heartbeatCount + 1;
                Boolean success = redisTemplate.opsForValue()
                        .setIfPresent(key, newCount, maxEffectiveSeconds, TimeUnit.SECONDS);
                if (success == null || !success) {
                    throw new IllegalStateException("WorkerId timeout!!!");
                }
                logger.debug("WorkerId: [{}] heartbeat success, total heartbeat {} times", workerId, newCount);
            } catch (Exception e) {
                logger.error("Failed to worker-id-heartbeat!, workerId: [{}] ", workerId, e);
                throw e;
            }
        }
    }
}
