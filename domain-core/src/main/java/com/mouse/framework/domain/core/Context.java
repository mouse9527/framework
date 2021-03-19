package com.mouse.framework.domain.core;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

public final class Context {
    private static Clock clock = Clock.systemUTC();
    private static ContextHolder contextHolder;

    private Context() {
    }

    public static Instant now() {
        return clock.instant();
    }

    public static Optional<User> current() {
        if (contextHolder == null) throw new ContextException("Context.contextHolder is null, please init it");
        return contextHolder.getUser();
    }

    static void setClock(Clock clock) {
        Context.clock = clock;
    }

    static void set(ContextHolder contextHolder) {
        Context.contextHolder = contextHolder;
    }
}
