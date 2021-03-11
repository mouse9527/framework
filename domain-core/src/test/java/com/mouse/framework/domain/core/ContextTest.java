package com.mouse.framework.domain.core;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

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

        ContextSetter.reset();
    }

    @Test
    void should_be_able_to_get_token() {
        Token token = mock(Token.class);
        given(token.getId()).willReturn("mock-id");
        given(token.getUser()).willReturn(mock(User.class));
        given(token.getAuthorities()).willReturn(new AuthoritiesSet(new Authority("mock-authority")));
        ContextSetter.set(() -> token);

        assertThat(Context.current()).isEqualTo(token);

        ContextSetter.set((TokenHolder) null);
    }

    @Test
    void should_be_able_to_raise_exception_when_token_loader_is_null() {
        Throwable throwable = catchThrowable(Context::current);

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(ContextException.class);
        assertThat(throwable).hasMessage("Context.tokenHolder is null, please init it");
    }
}
