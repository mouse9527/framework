package com.mouse.framework.security.authorization;

import com.mouse.framework.security.SecurityException;
import lombok.Generated;

@Generated
@Deprecated
public class IllegalTokenException extends SecurityException {
    public IllegalTokenException(Throwable throwable) {
        super("error.illegal-token", throwable);
    }

    public IllegalTokenException() {
        this(null);
    }
}
