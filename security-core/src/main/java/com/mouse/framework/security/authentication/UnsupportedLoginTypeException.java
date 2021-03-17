package com.mouse.framework.security.authentication;

import com.mouse.framework.security.SecurityException;

public class UnsupportedLoginTypeException extends SecurityException {
    public UnsupportedLoginTypeException() {
        super("error.unsupported-login-type");
    }
}
