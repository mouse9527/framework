package com.mouse.framework.jwt;

import com.mouse.framework.security.SecurityException;
import lombok.Generated;

@Generated
public class JWTException extends SecurityException {
    public JWTException(Throwable throwable) {
        super("error.system-error", throwable);
    }

    public JWTException(String message) {
        super(message);
    }

    public JWTException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
