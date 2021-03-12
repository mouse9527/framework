package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.TokenHolder;

public class ThreadLocalTokenHolder implements TokenHolder {
    private final ThreadLocal<Token> holder;

    public ThreadLocalTokenHolder() {
        this.holder = new ThreadLocal<>();
    }

    @Override
    public Token get() {
        return holder.get();
    }

    @Override
    public void refresh(Token token) {
        this.holder.set(token);
    }

    @Override
    public void clean() {
        this.holder.remove();
    }
}
