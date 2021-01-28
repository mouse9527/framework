package com.mouse.framework.sequence.core.snowflake;

import com.mouse.framework.sequence.core.SequenceService;

public class SnowFlakeSequenceService implements SequenceService {
    private final long sequenceBits = 12L;
    private final long workerIdBits = 10L;
    // 2020-01-01T00:00:00Z
    private final long startTimestamp = 1577836800000L;

    private final long sequenceMask = ~(-1L << sequenceBits);

    private final long workerIdShift = sequenceBits;
    private final long timestampShift = workerIdBits + sequenceBits;

    private final long workerId;

    private long lastTimestamp;

    private long sequence;

    public SnowFlakeSequenceService(long workerId) {
        this.workerId = workerId;
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
