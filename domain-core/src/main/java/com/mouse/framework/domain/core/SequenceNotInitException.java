package com.mouse.framework.domain.core;

public class SequenceNotInitException extends IllegalStateException {
    public SequenceNotInitException() {
        super("Sequence has not SequenceService!");
    }
}
