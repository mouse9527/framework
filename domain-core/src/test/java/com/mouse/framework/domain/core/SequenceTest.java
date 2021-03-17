package com.mouse.framework.domain.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class SequenceTest {

    @AfterEach
    void tearDown() {
        SequenceSetter.set(null);
    }

    @Test
    void should_be_able_to_get_next_when_reset_sequence_service() {
        SequenceService sequenceService = mock(SequenceService.class);
        given(sequenceService.next()).willReturn(1L);
        SequenceSetter.set(sequenceService);

        assertThat(Sequence.next()).isEqualTo(1L);
    }

    @Test
    void should_be_able_to_get_next_str_id() {
        SequenceService sequenceService = mock(SequenceService.class);
        given(sequenceService.nextStr()).willReturn("mock-seq");
        SequenceSetter.set(sequenceService);

        assertThat(Sequence.nextStr()).isEqualTo("mock-seq");
    }

    @Test
    void should_be_able_to_raise_exception_when_not_reset() {
        Throwable throwable = catchThrowable(Sequence::next);

        assertSequenceServiceNotInit(throwable);
        assertSequenceServiceNotInit(catchThrowable(Sequence::nextStr));
    }

    private void assertSequenceServiceNotInit(Throwable throwable) {
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(SequenceNotInitException.class);
        assertThat(throwable).hasMessage("Sequence has not SequenceService!");
    }
}
