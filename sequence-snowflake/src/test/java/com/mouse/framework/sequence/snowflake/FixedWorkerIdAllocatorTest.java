package com.mouse.framework.sequence.snowflake;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Disabled
@SpringBootTest(properties = {"sequence.snowflake.worker-id.allocator-type=fixed", "sequence.snowflake.worker-id.id=1"})
public class FixedWorkerIdAllocatorTest {
    @Resource
    private WorkerIdAllocator workerIdAllocator;

    @Test
    void should_be_able_to_Name() {
//        workerIdAllocator.allocate();
    }
}
