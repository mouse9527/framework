package com.mouse.framework.security;

import com.mouse.framework.domain.core.AuthoritiesSet;
import com.mouse.framework.domain.core.ContextSetter;
import com.mouse.framework.domain.core.User;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;

class TokenTest {

    @Test
    void should_be_able_to_effective_with_now() {
        Token token = createToken();

        assertThat(token.isEffective()).isFalse();

        ContextSetter.set(Clock.fixed(Instant.now().minus(1, DAYS), ZoneOffset.ofHours(8)));

        assertThat(token.isEffective()).isTrue();

        ContextSetter.set(Clock.systemUTC());
    }

    private Token createToken() {
        return new Token() {
            final Instant exp = Instant.now();

            @Override
            public String getId() {
                return null;
            }

            @Override
            public User getUser() {
                return null;
            }

            @Override
            public Instant getIssuedAt() {
                return null;
            }

            @Override
            public Instant getExpirationTime() {
                return exp;
            }

            @Override
            public AuthoritiesSet getAuthorities() {
                return null;
            }
        };
    }
}
