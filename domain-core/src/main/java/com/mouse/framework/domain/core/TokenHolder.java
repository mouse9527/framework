package com.mouse.framework.domain.core;

public interface TokenHolder {
    Token get();

    void refresh(Token token);

    void clean();
}
