package com.mouse.framework.security;

import lombok.Generated;

@Generated
public class IllegalTokenException extends AuthenticationException {
    public IllegalTokenException(Throwable throwable) {
        super("error.illegal-token", throwable);
    }

    public IllegalTokenException() {
        this(null);
    }
}
