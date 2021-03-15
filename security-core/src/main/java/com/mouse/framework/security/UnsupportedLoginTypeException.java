package com.mouse.framework.security;

public class UnsupportedLoginTypeException extends SecurityException {
    public UnsupportedLoginTypeException() {
        super("error.unsupported-login-type");
    }
}
