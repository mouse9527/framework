package com.mouse.framework.domain.core;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ContextTest {

    @Test
    void should_be_able_to_get_now() {
        Instant now = Context.now();

        assertThat(now).isNotNull();
        assertThat(now).isBefore(Context.now());
    }

    @Test
    void should_be_able_to_get_fixed_instant() {
        Instant fixedInstant = Instant.EPOCH;
        ContextSetter.set(Clock.fixed(fixedInstant, ZoneOffset.ofHours(8)));

        assertThat(Context.now()).isNotNull();
        assertThat(Context.now()).isEqualTo(fixedInstant);
        assertThat(Context.now()).isEqualTo(Context.now());

        ContextSetter.resetClock();
    }

    @Test
    void should_be_able_to_get_token() {
        User user = mock(User.class);
        ContextHolder contextHolder = mock(ContextHolder.class);
        given(contextHolder.getUser()).willReturn(Optional.of(user));
        ContextSetter.set(contextHolder);

        assertThat(Context.current()).isEqualTo(Optional.of(user));

        ContextSetter.set((ContextHolder) null);
    }

    @Test
    void should_be_able_to_raise_exception_when_token_loader_is_null() {
        Throwable throwable = catchThrowable(Context::current);

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(ContextException.class);
        assertThat(throwable).hasMessage("Context.contextHolder is null, please init it");
    }
}
