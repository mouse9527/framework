package com.mouse.framework.domain.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class SequenceTest {

    @AfterEach
    void tearDown() {
        SequenceSetter.reset(null);
    }

    @Test
    void should_be_able_to_get_next_when_reset_sequence_service() {
        SequenceSetter.reset(() -> 1L);

        assertThat(Sequence.next()).isEqualTo(1L);
    }

    @Test
    void should_be_able_to_raise_exception_when_not_reset() {
        Throwable throwable = catchThrowable(Sequence::next);

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(SequenceNotInitException.class);
        assertThat(throwable).hasMessage("Sequence has not SequenceService!");
    }
}
