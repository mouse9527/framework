package com.mouse.framework.domain.core;

import java.time.Clock;

public final class ContextSetter {
    private ContextSetter() {
    }

    public static void set(Clock clock) {
        Context.setClock(clock);
    }

    public static void resetClock() {
        Context.setClock(Clock.systemUTC());
    }

    public static void set(ContextHolder contextHolder) {
        Context.set(contextHolder);
    }
}
