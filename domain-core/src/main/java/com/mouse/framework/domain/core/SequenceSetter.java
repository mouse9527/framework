package com.mouse.framework.domain.core;

public final class SequenceSetter {
    private SequenceSetter() {
    }

    public static void set(SequenceService sequenceService) {
        Sequence.set(sequenceService);
    }

    public static void mock(Object... args) {
        Sequence.set(new FixedSequenceService(args));
    }

    public static void clean() {
        Sequence.set(null);
    }
}
