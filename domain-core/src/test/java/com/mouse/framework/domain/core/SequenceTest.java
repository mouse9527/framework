package com.mouse.framework.domain.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SequenceTest {

    @Test
    void should_be_able_to_get_next_when_reset_sequence_service() {
        SequenceSetter.reset(() -> 1L);

        assertThat(Sequence.next()).isEqualTo(1L);
    }
}
