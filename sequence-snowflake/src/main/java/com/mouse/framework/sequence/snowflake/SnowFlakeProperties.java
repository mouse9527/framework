package com.mouse.framework.sequence.snowflake;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

@Setter
@Generated
@ConfigurationProperties("sequence.snowflake")
public class SnowFlakeProperties {
    private static final Long DEFAULT_SEQUENCE_BITS = 12L;
    private static final Long DEFAULT_WORKER_ID_BITS = 10L;
    // 2020-01-01T00:00:00Z
    private static final Long DEFAULT_START_TIMESTAMP = 1577836800000L;

    private Long sequenceBits;
    private Long workerIdBits;
    private Long startTimestamp;
    private WorkerIdProperties workerId;

    public Long getSequenceBits() {
        return getOrDefault(sequenceBits, DEFAULT_SEQUENCE_BITS);
    }

    private Long getOrDefault(Long value, Long defaultValue) {
        return value == null ? defaultValue : value;
    }

    public Long getWorkerIdBits() {
        return getOrDefault(workerIdBits, DEFAULT_WORKER_ID_BITS);
    }

    public Long getStartTimestamp() {
        return getOrDefault(startTimestamp, DEFAULT_START_TIMESTAMP);
    }

    public WorkerIdProperties getWorkerId() {
        return workerId;
    }

    long getMaxWorkerId() {
        return (1L << getWorkerIdBits()) - 1;
    }

    @Setter
    @Getter
    static class WorkerIdProperties {
        private String keyPrefix;
        private Long heartbeatIntervalSeconds;
        private Integer maxFailedTimes;

        public void setKeyPrefix(String keyPrefix) {
            this.keyPrefix = keyPrefix;
        }

        String createKey(long workerId) {
            return String.format("%s:%d", getKeyPrefix(), workerId);
        }

        long getMaxEffectiveSeconds() {
            return heartbeatIntervalSeconds * maxFailedTimes;
        }

        long getHeartBeatIntervalMillisecond() {
            return TimeUnit.SECONDS.toMillis(heartbeatIntervalSeconds);
        }
    }
}
