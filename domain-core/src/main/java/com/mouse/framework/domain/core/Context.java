package com.mouse.framework.domain.core;

import java.time.Clock;
import java.time.Instant;

public final class Context {
    private static Clock clock = Clock.systemUTC();
    private static TokenRepository tokenRepository;

    private Context() {
    }

    public static Instant now() {
        return clock.instant();
    }

    public static Token current() {
        return tokenRepository.load();
    }

    static void setClock(Clock clock) {
        synchronized (Context.class) {
            Context.clock = clock;
        }
    }

    static void set(TokenRepository tokenRepository) {
        synchronized (Context.class) {
            Context.tokenRepository = tokenRepository;
        }
    }
}
