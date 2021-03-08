package com.mouse.framework.sequence.snowflake;

import com.mouse.framework.domain.core.Sequence;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(properties = {"sequence.snowflake.worker-id.id=1",
        "sequence.snowflake.worker-id.allocator-type=fixed"})
public class SequenceTest {

    @Test
    void should_be_able_to_auto_config_sequence() {
        assertThat(Sequence.next()).isNotNull();
    }
}
