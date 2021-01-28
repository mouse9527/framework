package com.mouse.framework.sequence.core.snowflake;

import com.mouse.framework.sequence.core.SequenceService;

public class SnowFlakeSequenceService implements SequenceService {
    public SnowFlakeSequenceService(Integer workerId) {
    }

    @Override
    public Long next() {
        return 1L;
    }
}
