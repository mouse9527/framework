package com.mouse.framework.domain.core;

import java.time.Clock;
import java.time.Instant;

public final class Context {
    private static Clock clock = Clock.systemUTC();

    private Context() {
    }

    public static Instant now() {
        return clock.instant();
    }

    static void setClock(Clock clock) {
        synchronized (Context.class) {
            Context.clock = clock;
        }
    }
}
