package com.mouse.framework.jwt.sign;

import com.mouse.framework.security.AuthenticationException;

public class JWTException extends AuthenticationException {
    public JWTException(Throwable throwable) {
        super("error.system-error", throwable);
    }
}
