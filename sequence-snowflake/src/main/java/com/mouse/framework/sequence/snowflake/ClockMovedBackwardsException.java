package com.mouse.framework.sequence.snowflake;

import lombok.Generated;

@Generated
public class ClockMovedBackwardsException extends IllegalStateException {
    public ClockMovedBackwardsException(Long milliseconds) {
        super(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", milliseconds));
    }
}
