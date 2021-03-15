package com.mouse.framework.security;

import com.mouse.framework.domain.core.TokenHolder;

public class AuthenticationService {
    private final TokenHolder tokenHolder;

    public AuthenticationService(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    public void requireLogged() {
        if (tokenHolder.get().isEmpty()) throw new IllegalTokenException();
    }
}
