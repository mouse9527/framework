package com.mouse.framework.sequence.snowflake;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(properties = {"sequence.snowflake.worker-id.id=1",
        "sequence.snowflake.worker-id.allocator-type=fixed"})
class FixedWorkerIdAllocatorTest {
    @Resource
    private WorkerIdAllocator workerIdAllocator;

    @Test
    void should_be_able_to_Name() {
        assertThat(workerIdAllocator.allocate()).isEqualTo(1);
        assertThat(workerIdAllocator.allocate()).isEqualTo(1);
        assertThat(workerIdAllocator.allocate()).isEqualTo(1);
    }
}
