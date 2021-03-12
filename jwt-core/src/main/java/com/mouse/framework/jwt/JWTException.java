package com.mouse.framework.jwt;

import com.mouse.framework.security.AuthenticationException;
import lombok.Generated;

@Generated
public class JWTException extends AuthenticationException {
    public JWTException(Throwable throwable) {
        super("error.system-error", throwable);
    }
}
