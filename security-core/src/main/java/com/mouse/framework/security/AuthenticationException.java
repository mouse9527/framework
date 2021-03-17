package com.mouse.framework.security;

import lombok.Generated;

@Generated
public abstract class AuthenticationException extends SecurityException {
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
