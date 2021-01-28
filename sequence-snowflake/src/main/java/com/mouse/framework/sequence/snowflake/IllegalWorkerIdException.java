package com.mouse.framework.sequence.snowflake;

import lombok.Generated;

@Generated
public class IllegalWorkerIdException extends IllegalArgumentException {
    public IllegalWorkerIdException(Long workerId, long maxWorkerId) {
        super(String.format("Illegal workerId %d, range: [0 ~ %d]", workerId, maxWorkerId));
    }
}
