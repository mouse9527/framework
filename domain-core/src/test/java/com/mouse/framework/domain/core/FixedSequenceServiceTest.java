package com.mouse.framework.domain.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class FixedSequenceServiceTest {
    private static Stream<Object> illegalTypes() {
        return Stream.of(null, 'a', new Object(), false);
    }

    @Test
    void should_be_able_to_get_next_seq() {
        SequenceService sequenceService = new FixedSequenceService(1L, "seq-1", "seq-2", 2L, 3L, "seq-3");

        assertThat(sequenceService.next()).isEqualTo(1L);
        assertThat(sequenceService.next()).isEqualTo(2L);
        assertThat(sequenceService.next()).isEqualTo(3L);
        assertThat(sequenceService.nextStr()).isEqualTo("seq-1");
        assertThat(sequenceService.nextStr()).isEqualTo("seq-2");
        assertThat(sequenceService.nextStr()).isEqualTo("seq-3");
    }

    @ParameterizedTest
    @MethodSource("illegalTypes")
    void should_be_able_to_raise_exception_with_illegal_type(Object illegalType) {
        Throwable throwable = catchThrowable(() -> new FixedSequenceService(illegalType));

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        assertThat(throwable).hasMessage("Illegal type of args");
    }

    @Test
    void should_be_able_to_raise_exception_when_no_seq() {
        SequenceService sequenceService = new FixedSequenceService();

        Throwable throwable = catchThrowable(sequenceService::next);

        assertThat(throwable).isInstanceOf(IllegalStateException.class);
        assertThat(throwable).hasMessage("Not enough long sequence");

        Throwable strThrowable = catchThrowable(sequenceService::nextStr);

        assertThat(strThrowable).isInstanceOf(IllegalStateException.class);
        assertThat(strThrowable).hasMessage("Not enough string sequence");
    }
}
