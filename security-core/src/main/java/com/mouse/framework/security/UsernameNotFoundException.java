package com.mouse.framework.security;

public class UsernameNotFoundException extends SecurityException {
    public UsernameNotFoundException() {
        super("error.username-not-found");
    }
}
