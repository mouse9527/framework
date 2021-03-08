package com.mouse.framework.sequence.snowflake;

import lombok.Generated;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Generated
@ConfigurationProperties("sequence.snowflake")
public class SnowFlakeProperties {
    private static final Long DEFAULT_SEQUENCE_BITS = 12L;
    private static final Long DEFAULT_WORKER_ID_BITS = 10L;
    // 2020-01-01T00:00:00Z
    private static final Long DEFAULT_START_TIMESTAMP = 1577836800000L;

    private Long startTimestamp;
    private Long workerIdBits;
    private Long sequenceBits;

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

    long getMaxWorkerId() {
        return (1L << getWorkerIdBits()) - 1;
    }
}
