package com.mouse.framework.security;

public abstract class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
