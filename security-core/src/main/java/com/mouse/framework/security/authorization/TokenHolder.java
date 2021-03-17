package com.mouse.framework.security.authorization;

import com.mouse.framework.security.Token;

import java.util.Optional;

public interface TokenHolder {
    Optional<Token> get();

    void refresh(Token token);

    void clean();
}
