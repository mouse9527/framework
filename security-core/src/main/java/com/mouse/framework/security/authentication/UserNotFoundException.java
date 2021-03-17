package com.mouse.framework.security.authentication;

import com.mouse.framework.security.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException() {
        super("error.user-not-found");
    }
}
