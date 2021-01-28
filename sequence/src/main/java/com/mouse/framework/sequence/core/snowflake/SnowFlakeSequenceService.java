package com.mouse.framework.sequence.core.snowflake;

import com.mouse.framework.sequence.core.SequenceService;

public class SnowFlakeSequenceService implements SequenceService {
    // 2020-01-01T00:00:00Z
    private final long startTimestamp;

    private final long sequenceMask;
    private final long workerIdShift;
    private final long timestampShift;

    private final long workerId;
    private long lastTimestamp;
    private long sequence;

    public SnowFlakeSequenceService(long workerId, SnowFlakeProperties properties) {
        this.startTimestamp = properties.getStartTimestamp();
        this.workerId = workerId;

        this.sequenceMask = ~(-1L << properties.getSequenceBits());
        this.workerIdShift = properties.getSequenceBits();
        this.timestampShift = (long) properties.getWorkerIdBits() + properties.getSequenceBits();
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
