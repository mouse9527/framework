package com.mouse.framework.sequence.core.snowflake;

public class ClockMovedBackwardsException extends IllegalStateException {
    public ClockMovedBackwardsException(Long milliseconds) {
        super(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", milliseconds));
    }
}
