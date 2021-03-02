package com.mouse.framework.domain.core;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

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
        ContextReSetter.set(Clock.fixed(fixedInstant, ZoneOffset.ofHours(8)));

        assertThat(Context.now()).isNotNull();
        assertThat(Context.now()).isEqualTo(fixedInstant);
        assertThat(Context.now()).isEqualTo(Context.now());

        ContextReSetter.reset();
    }
}
