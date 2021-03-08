package com.mouse.framework.sequence.snowflake;

public interface WorkerIdAllocator {
    long allocate();

    void recycle(long workerId);
}
