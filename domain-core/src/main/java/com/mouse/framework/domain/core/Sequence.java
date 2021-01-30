package com.mouse.framework.domain.core;

public class Sequence {
    private static SequenceService sequenceService;

    public static Long next() {
        return sequenceService.next();
    }

    static void reset(SequenceService sequenceService) {
        Sequence.sequenceService = sequenceService;
    }
}
