package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.TokenHolder;

public class ThreadLocalTokenHolder implements TokenHolder {
    private Token token;

    @Override
    public Token get() {
        return token;
    }

    @Override
    public void refresh(Token token) {
        this.token = token;
    }

    @Override
    public void clean() {

    }
}
