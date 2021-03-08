package com.mouse.framework.sequence.snowflake;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest(properties = {"sequence.snowflake.worker-id.id=1",
        "sequence.snowflake.worker-id.allocator-type=fixed"})
class FixedWorkerIdAllocatorTest {
    @Resource
    private WorkerIdAllocator workerIdAllocator;

    @Test
    void should_be_able_to_allocate_fixed_id() {
        assertThat(workerIdAllocator.allocate()).isEqualTo(1);
        assertThat(workerIdAllocator.allocate()).isEqualTo(1);
        assertThat(workerIdAllocator.allocate()).isEqualTo(1);
    }

    @Test
    void should_be_able_to_raise_exception_with_recycle() {
        Throwable throwable = catchThrowable(() -> workerIdAllocator.recycle(1));

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
        assertThat(throwable).hasMessage("Can't recycle fixed worker-id");
    }
}
