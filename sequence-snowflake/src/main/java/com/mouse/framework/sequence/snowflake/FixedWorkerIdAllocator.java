package com.mouse.framework.sequence.snowflake;

public class FixedWorkerIdAllocator implements WorkerIdAllocator {
    private final WorkerIdProperties properties;

    public FixedWorkerIdAllocator(WorkerIdProperties properties) {
        this.properties = properties;
    }

    @Override
    public long allocate() {
        return properties.getId();
    }

    @Override
    public void recycle(long workerId) {
        throw new UnsupportedOperationException("Can't recycle fixed worker-id");
    }
}
