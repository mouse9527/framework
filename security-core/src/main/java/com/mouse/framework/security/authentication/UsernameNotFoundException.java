package com.mouse.framework.security.authentication;

import com.mouse.framework.security.AuthenticationException;

public class UsernameNotFoundException extends AuthenticationException {
    public UsernameNotFoundException() {
        super("error.username-not-found");
    }
}
