package com.mouse.framework.domain.core;

public final class SequenceSetter {
    private SequenceSetter() {
    }

    public static void set(SequenceService sequenceService) {
        Sequence.set(sequenceService);
    }
}
