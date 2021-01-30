package com.mouse.framework.domain.core;

public final class Sequence {
    private static SequenceService sequenceService;

    private Sequence() {
    }

    public static Long next() {
        if (sequenceService == null) throw new SequenceNotInitException();
        return sequenceService.next();
    }

    static void reset(SequenceService sequenceService) {
        Sequence.sequenceService = sequenceService;
    }
}
