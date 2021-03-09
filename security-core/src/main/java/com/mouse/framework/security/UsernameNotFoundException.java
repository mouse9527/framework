package com.mouse.framework.security;

public class UsernameNotFoundException extends AuthenticationException {
    public UsernameNotFoundException() {
        super("error.username-not-found");
    }
}
