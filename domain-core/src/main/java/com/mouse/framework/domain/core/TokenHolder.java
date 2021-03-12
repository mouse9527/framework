package com.mouse.framework.domain.core;

import java.util.Optional;

public interface TokenHolder {
    Optional<Token> get();

    void refresh(Token token);

    void clean();
}
