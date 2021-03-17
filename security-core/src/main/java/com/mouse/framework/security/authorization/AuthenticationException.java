package com.mouse.framework.security.authorization;

import com.mouse.framework.security.SecurityException;

public class AuthenticationException extends SecurityException {
    public AuthenticationException(String message) {
        super(message);
    }
}
