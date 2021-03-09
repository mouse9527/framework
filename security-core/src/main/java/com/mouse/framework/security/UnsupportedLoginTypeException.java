package com.mouse.framework.security;

public class UnsupportedLoginTypeException extends AuthenticationException {
    public UnsupportedLoginTypeException() {
        super("error.unsupported-login-type");
    }
}
