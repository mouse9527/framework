package com.mouse.framework.sequence.snowflake;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkerIdAllocatorTest {

    @Test
    void should_be_able_to_allocate_worker_id() {
        WorkerIdAllocator workerIdAllocator = new RedisWorkerIdAllocator();

        long workerId = workerIdAllocator.get();

        assertThat(workerId).isNotNull();
        assertThat(workerId).isGreaterThan(-1);
    }
}
