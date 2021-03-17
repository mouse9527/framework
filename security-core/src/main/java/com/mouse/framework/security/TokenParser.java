package com.mouse.framework.security;

import java.util.Optional;

public interface TokenParser {
    Optional<Token> parse(String text);
}
