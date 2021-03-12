package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;
import com.mouse.framework.domain.core.TokenHolder;

import java.util.Optional;

public class ThreadLocalTokenHolder implements TokenHolder {
    private final ThreadLocal<Token> holder;

    public ThreadLocalTokenHolder() {
        this.holder = new ThreadLocal<>();
    }

    @Override
    public Optional<Token> get() {
        return Optional.ofNullable(holder.get());
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
