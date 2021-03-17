package com.mouse.framework.security.authorization;

import com.mouse.framework.security.Token;

import java.util.Optional;

public interface TokenParser {
    Optional<Token> parse(String text);
}
