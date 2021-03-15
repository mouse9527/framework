package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;

import java.util.Optional;

public interface TokenParser {
    Optional<Token> parse(String text);
}
