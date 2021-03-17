package com.mouse.framework.security;

import java.util.Optional;

public interface TokenHolder {
    Optional<Token> get();

    void refresh(Token token);

    void clean();
}
