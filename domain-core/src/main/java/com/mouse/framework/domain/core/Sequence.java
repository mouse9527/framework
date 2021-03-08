package com.mouse.framework.domain.core;

public final class Sequence {
    private static SequenceService sequenceService;

    private Sequence() {
    }

    public static Long next() {
        if (sequenceService == null) throw new SequenceNotInitException();
        return sequenceService.next();
    }

    static void set(SequenceService sequenceService) {
        synchronized (Sequence.class) {
            Sequence.sequenceService = sequenceService;
        }
    }

    public static String nextStr() {
        return String.valueOf(next());
    }
}
