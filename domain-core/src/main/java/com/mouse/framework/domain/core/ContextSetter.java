package com.mouse.framework.domain.core;

import java.time.Clock;

public final class ContextSetter {
    private ContextSetter() {
    }

    public static void set(Clock clock) {
        Context.setClock(clock);
    }

    public static void reset() {
        Context.setClock(Clock.systemUTC());
    }

    public static void set(TokenHolder tokenHolder) {
        Context.set(tokenHolder);
    }
}
