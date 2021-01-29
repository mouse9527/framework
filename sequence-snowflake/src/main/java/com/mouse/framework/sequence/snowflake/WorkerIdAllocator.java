package com.mouse.framework.sequence.snowflake;

public interface WorkerIdAllocator {
    long allocate(long maxWorkerId);

    void recycle(long workerId);
}
