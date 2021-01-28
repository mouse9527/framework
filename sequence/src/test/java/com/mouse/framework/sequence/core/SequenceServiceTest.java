package com.mouse.framework.sequence.core;

import com.mouse.framework.sequence.core.snowflake.SnowFlakeSequenceService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SequenceServiceTest {

    @Test
    void should_be_able_to_create_next_seq() {
        SequenceService sequenceService = new SnowFlakeSequenceService(1);

        Long id = sequenceService.next();

        assertThat(id).isNotNull();
        assertThat(id).isGreaterThan(0);
    }
}
