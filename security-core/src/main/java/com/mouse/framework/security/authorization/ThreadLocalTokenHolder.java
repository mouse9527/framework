package com.mouse.framework.security.authorization;

import com.mouse.framework.domain.core.ContextHolder;
import com.mouse.framework.domain.core.User;
import com.mouse.framework.security.Token;

import java.util.Optional;

public class ThreadLocalTokenHolder implements TokenHolder, ContextHolder {
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

    @Override
    public Optional<User> getUser() {
        return get().map(Token::getUser);
    }
}
