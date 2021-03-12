package com.mouse.framework.jwt.verify;

import com.mouse.framework.security.AuthenticationException;

public class IllegalTokenException extends AuthenticationException {
    public IllegalTokenException(Throwable throwable) {
        super("error.illegal-token", throwable);
    }

    public IllegalTokenException() {
        this(null);
    }
}
