package com.mouse.framework.sequence.core.snowflake;


public class SnowFlakeProperties {
    private static final Long DEFAULT_SEQUENCE_BITS = 12L;
    private static final Long DEFAULT_WORKER_ID_BITS = 10L;
    private static final Long DEFAULT_START_TIMESTAMP = 1577836800000L;
    private Long sequenceBits;
    private Long workerIdBits;
    private Long startTimestamp;

    public Long getSequenceBits() {
        return getOrDefault(sequenceBits, DEFAULT_SEQUENCE_BITS);
    }

    public void setSequenceBits(Long sequenceBits) {
        this.sequenceBits = sequenceBits;
    }

    private Long getOrDefault(Long value, Long defaultValue) {
        return value == null ? defaultValue : value;
    }

    public Long getWorkerIdBits() {
        return getOrDefault(workerIdBits, DEFAULT_WORKER_ID_BITS);
    }

    public void setWorkerIdBits(Long workerIdBits) {
        this.workerIdBits = workerIdBits;
    }

    public Long getStartTimestamp() {
        return getOrDefault(startTimestamp, DEFAULT_START_TIMESTAMP);
    }

    public void setStartTimestamp(Long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }
}
