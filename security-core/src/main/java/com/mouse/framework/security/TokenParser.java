package com.mouse.framework.security;

import com.mouse.framework.domain.core.Token;

public interface TokenParser {
    Token parse(String text);
}
