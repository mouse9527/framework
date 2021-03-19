package com.mouse.framework.domain.core;

public final class Sequence {
    private static SequenceService sequenceService;

    private Sequence() {
    }

    public static Long next() {
        return getService().next();
    }

    public static String nextStr() {
        return getService().nextStr();
    }

    private static SequenceService getService() {
        if (sequenceService == null) throw new SequenceNotInitException();
        return Sequence.sequenceService;
    }

    static void set(SequenceService sequenceService) {
        Sequence.sequenceService = sequenceService;
    }
}
