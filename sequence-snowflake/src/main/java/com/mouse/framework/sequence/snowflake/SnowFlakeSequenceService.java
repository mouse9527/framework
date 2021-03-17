package com.mouse.framework.sequence.snowflake;

import com.mouse.framework.domain.core.SequenceService;
import lombok.Generated;

@Generated
public class SnowFlakeSequenceService implements SequenceService {
    private final long startTimestamp;

    private final long sequenceMask;
    private final long workerIdShift;
    private final long timestampShift;

    private final long workerId;
    private long lastTimestamp;
    private long sequence;

    public SnowFlakeSequenceService(long workerId, SnowFlakeProperties properties) {
        validate(workerId, properties.getMaxWorkerId());
        this.workerId = workerId;
        this.startTimestamp = properties.getStartTimestamp();
        this.sequenceMask = ~(-1L << properties.getSequenceBits());
        this.workerIdShift = properties.getSequenceBits();
        this.timestampShift = (long) properties.getWorkerIdBits() + properties.getSequenceBits();
    }

    private void validate(long workerId, long maxWorkerId) {
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalWorkerIdException(workerId, maxWorkerId);
        }
    }

    @Override
    public synchronized Long next() {
        long current = now();

        if (current < lastTimestamp) {
            throw new ClockMovedBackwardsException(lastTimestamp - current);
        }

        if (current == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                current = nextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = current;

        return (current - startTimestamp) << timestampShift
                | workerId << workerIdShift
                | sequence;
    }

    @Override
    public String nextStr() {
        return String.valueOf(next());
    }

    private long nextMillis(long lastTimestamp) {
        long timestamp = now();
        while (timestamp <= lastTimestamp) {
            timestamp = now();
        }
        return timestamp;
    }

    private long now() {
        return System.currentTimeMillis();
    }
}
