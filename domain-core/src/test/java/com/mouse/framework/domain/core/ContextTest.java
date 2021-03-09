package com.mouse.framework.domain.core;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
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
        given(token.getUsername()).willReturn("mock-username");
        doReturn(Collections.singleton((Authority)() -> "mock-authority")).when(token).getAuthorities();
        given(token.getUserId()).willReturn("mock-user-id");
        ContextSetter.set(() -> token);

        assertThat(Context.current()).isEqualTo(token);
    }
}
