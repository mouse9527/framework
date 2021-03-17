package com.mouse.framework.security.authentication;

import com.mouse.framework.security.SecurityException;

public class UsernameNotFoundException extends SecurityException {
    public UsernameNotFoundException() {
        super("error.username-not-found");
    }
}
