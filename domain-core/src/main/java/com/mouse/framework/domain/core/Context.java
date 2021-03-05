package com.mouse.framework.domain.core;

import java.time.Clock;
import java.time.Instant;

public final class Context {
    private static Clock clock;

    private Context() {

    }

    static {
        clock = Clock.systemUTC();
    }

    public static Instant now() {
        return clock.instant();
    }

    static void setClock(Clock clock) {
        Context.clock = clock;
    }
}
