package com.mouse.framework.domain.core;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

public final class Context {
    private static Clock clock = Clock.systemUTC();
    private static TokenHolder tokenHolder;

    private Context() {
    }

    public static Instant now() {
        return clock.instant();
    }

    public static Optional<Token> current() {
        if (tokenHolder == null) throw new ContextException("Context.tokenHolder is null, please init it");
        return tokenHolder.get();
    }

    static void setClock(Clock clock) {
        synchronized (Context.class) {
            Context.clock = clock;
        }
    }

    static void set(TokenHolder tokenHolder) {
        synchronized (Context.class) {
            Context.tokenHolder = tokenHolder;
        }
    }
}
