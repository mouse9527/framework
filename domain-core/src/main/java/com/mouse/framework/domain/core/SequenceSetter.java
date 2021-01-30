package com.mouse.framework.domain.core;

public final class SequenceSetter {
    private SequenceSetter() {
    }

    public static void reset(SequenceService sequenceService) {
        Sequence.reset(sequenceService);
    }
}
