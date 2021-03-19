package com.mouse.framework.domain.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SequenceSetterTest {

    @Test
    void should_be_able_to_mock_sequence() {
        SequenceSetter.mock("mock-id-1", 1L, "mock-id-2", 2L, 3L, "mock-id-3");

        assertThat(Sequence.next()).isEqualTo(1L);
        assertThat(Sequence.nextStr()).isEqualTo("mock-id-1");
        assertThat(Sequence.next()).isEqualTo(2L);
        assertThat(Sequence.nextStr()).isEqualTo("mock-id-2");
        assertThat(Sequence.nextStr()).isEqualTo("mock-id-3");
        assertThat(Sequence.next()).isEqualTo(3L);

        SequenceSetter.clean();
    }

    @Test
    void should_be_able_to_clean_set() {
        SequenceSetter.mock("mock-id-1");

        SequenceSetter.clean();

        Throwable throwable = catchThrowable(Sequence::next);

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(SequenceNotInitException.class);
    }
}
