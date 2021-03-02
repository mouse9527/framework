package com.mouse.framework.domain.core;

import java.time.Clock;

public class ContextReSetter {
    public static void set(Clock clock) {
        Context.setClock(clock);
    }

    public static void reset() {
        Context.setClock(Clock.systemUTC());
    }
}
