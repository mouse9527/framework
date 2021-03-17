package com.mouse.framework.security.authorization;

import com.mouse.framework.security.Token;

public class AuthorizationService {
    private final TokenHolder tokenHolder;

    public AuthorizationService(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    public void requireLogged() {
        if (tokenHolder.get().isEmpty())
            throw new UnLoggedException("error.un-logged");
    }

    public void requireAuthorities(String... authorities) {
        requireLogged();
        //Has been asserted to be non-empty
        //noinspection OptionalGetWithoutIsPresent
        Token token = tokenHolder.get().get();
        if (!token.getAuthorities().contains(authorities))
            throw new AccessDeniedException("error.access-denied");
    }
}
